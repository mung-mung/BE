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
    public List<Article> getAllPost() {
        return articleRepository.findAll();
    }

    @Transactional
    public ArticleDto getPostById(Integer id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid article ID"));
        return new ArticleDto(article);
    }

    @Transactional
    public ArticleDto createArticle(ArticleDto articleDto) {
        if (articleDto.getWriterId() == null) {
            throw new IllegalArgumentException("writer ID must not be null");
        }

        UserAccount writer = userAccountRepository.findById(articleDto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("user is not found"));
        Article newArticle = new Article(articleDto.getTitle(), articleDto.getContent(), writer);
        Article savedArticle = articleRepository.save(newArticle);

        return new ArticleDto(savedArticle);
    }


    @Transactional
    public ArticleDto updatePost(Integer id, ArticleDto updatedPost) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));
        article.update(updatedPost.getTitle(), updatedPost.getContent());

        Article savedArticle = articleRepository.save(article);

        return new ArticleDto(savedArticle);
    }

    @Transactional
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

}
