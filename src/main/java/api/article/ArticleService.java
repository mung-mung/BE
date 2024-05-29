package api.article;

import api.article.dto.ArticleDto;
import api.user.userAccount.*;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public ArticleService(ArticleRepository articleRepository, UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.userAccountRepository = userAccountRepository;
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
    public ArticleDto createArticle(ArticleDto articleDto) {
        if (articleDto.getOwnerId() == null) {
            throw new IllegalArgumentException("Owner ID must not be null");
        }

        UserAccount owner = userAccountRepository.findById(articleDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("user is not found"));
        Article newArticle = new Article(articleDto.getTitle(), owner, articleDto.getArticleContractDetail());
        Article savedArticle = articleRepository.save(newArticle);

        return new ArticleDto(savedArticle);
    }


    @Transactional
    public ArticleDto updateArticle(Integer id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));
        Article updatedArticle = article.update(articleDto.getTitle(), articleDto.getArticleContractDetail());
        Article savedArticle = articleRepository.save(updatedArticle);
        return new ArticleDto(savedArticle);
    }

    @Transactional
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

}
