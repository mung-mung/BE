package api.article;

import api.article.dto.ArticleDto;
import api.article.dto.CreateContractDto;
import api.common.util.auth.loggedInUser.LoggedInUser;
import api.owning.Owning;
import api.owning.OwningRepository;
import api.user.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.OwnerRepository;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final OwnerRepository ownerRepository;
    private final OwningRepository owningRepository;

    public ArticleService(ArticleRepository articleRepository, OwnerRepository ownerRepository, OwningRepository owningRepository) {
        this.articleRepository = articleRepository;
        this.ownerRepository = ownerRepository;
        this.owningRepository = owningRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for(Article article : articles){
            articleDtos.add(new ArticleDto(article));
        }
        return articleDtos;
    }

    @Transactional
    public ArticleDto createArticle(CreateContractDto createContractDto) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<Owning> optionalOwning = owningRepository.findByOwnerIdAndDogId(loggedInOwner.getId(), createContractDto.getDogId());
        if (optionalOwning.isEmpty()) {
            throw new AccessDeniedException("Only owner of the dog can create article.");
        }
        Owning owning = optionalOwning.get();

        Article newArticle = new Article(owning, createContractDto.getArticleContractDetail());
        Article savedArticle = articleRepository.save(newArticle);

        return new ArticleDto(savedArticle);
    }


    @Transactional
    public ArticleDto updateArticleContractDetail(Integer articleId, ArticleContractDetail articleContractDetail) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new EntityNotFoundException("Article not found with ID: " + articleId);
        }
        Article article = optionalArticle.get();
        if (!article.getOwning().getOwner().equals(loggedInOwner)) {
            throw new AccessDeniedException("Only the author can update their own article.");
        }
        Article updatedArticle = article.update(articleContractDetail);
        Article savedArticle = articleRepository.save(updatedArticle);
        return new ArticleDto(savedArticle);
    }

    @Transactional
    public void deleteArticle(Integer articleId) throws AccessDeniedException {
        Owner loggedInOwner = loggedInOwner();
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            throw new EntityNotFoundException("Article not found with ID: " + articleId);
        }
        Article article = optionalArticle.get();
        if (!article.getOwning().getOwner().equals(loggedInOwner)) {
            throw new AccessDeniedException("Only the author can delete their own article.");
        }
        articleRepository.deleteById(articleId);
    }

    private Owner loggedInOwner() throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can update article.");
        }

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Invalid owner account");
        }

        return optionalOwner.get();
    }

}
