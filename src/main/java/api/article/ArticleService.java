package api.article;

import api.article.dto.ArticleDto;
import api.common.util.auth.loggedInUser.LoggedInUser;
import api.user.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.OwnerRepository;
import api.user.userAccount.*;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final OwnerRepository ownerRepository;

    public ArticleService(ArticleRepository articleRepository, OwnerRepository ownerRepository) {
        this.articleRepository = articleRepository;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    @Transactional
    public ArticleDto getArticleById(Integer id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid article ID"));
        return new ArticleDto(article);
    }

    @Transactional
    public ArticleDto createArticle(ArticleDto articleDto) throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can create article.");
        }
        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }
        if (articleDto.getOwnerId() == null) {
            throw new IllegalArgumentException("Owner ID must not be null");
        }
        Owner owner = optionalOwner.get();
        Article newArticle = new Article(owner, articleDto.getArticleContractDetail());
        Article savedArticle = articleRepository.save(newArticle);

        return new ArticleDto(savedArticle);
    }


    @Transactional
    public ArticleDto updateArticle(Integer id, ArticleDto articleDto) throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can update article.");
        }

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }

        if (articleDto.getOwnerId() == null) {
            throw new IllegalArgumentException("Owner ID must not be null");
        }

        Owner owner = optionalOwner.get();
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        if (!article.getOwner().equals(owner)) {
            throw new AccessDeniedException("Only the author can update their own post.");
        }

        Article updatedArticle = article.update(articleDto.getArticleContractDetail());
        Article savedArticle = articleRepository.save(updatedArticle);
        return new ArticleDto(savedArticle);
    }

    @Transactional
    public void deleteArticle(Integer id) throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can update article.");
        }

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }

        Owner owner = optionalOwner.get();
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        if (!article.getOwner().equals(owner)) {
            throw new AccessDeniedException("Only the author can delete their own post.");
        }
        articleRepository.deleteById(id);
    }

}
