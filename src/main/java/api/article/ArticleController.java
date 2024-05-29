package api.article;

import api.article.dto.ArticleDto;
import api.common.util.http.HttpResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/board")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> getAllArticle(){
        try {
            return HttpResponse.successOk("All articles found successfully", articleService.getAllPost());
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to fetch articles: " + e.getMessage(), null);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> createArticle(@RequestBody ArticleDto articleDto) {
        try {
            ArticleDto createdArticleDto = articleService.createArticle(articleDto);
            return HttpResponse.successCreated("Article successfully created.", createdArticleDto);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating article: " + e.getMessage(), null);
        }
    }

    @GetMapping("/{articleId}")
    @ResponseBody
        public ResponseEntity<Object> getPostById(@PathVariable Integer articleId) {
        try {
            ArticleDto foundArticle = articleService.getPostById(articleId);

            if (foundArticle != null) {
                return HttpResponse.successOk("Article found successfully", foundArticle);
            } else {
                return HttpResponse.notFound("Article not found with ID: " + articleId, null);
            }
        } catch (Exception e) {
            return HttpResponse.internalError("Error finding Article: " + e.getMessage(), null);
        }
    }

    @PatchMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> updateArticle(@PathVariable Integer articleId, @RequestBody ArticleDto uewArticleDto) {
        ArticleDto updatedArticleDto = articleService.updatePost(articleId, uewArticleDto);
        if (updatedArticleDto != null) {
            return HttpResponse.successOk("Article successfully updated", updatedArticleDto);
        } else {
            return HttpResponse.notFound("Article not found", null);
        }
    }

    @DeleteMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> deletePost(@PathVariable Integer articleId) {
        try {
            articleService.deleteArticle(articleId);
            return HttpResponse.successOk("Article deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting article: " + e.getMessage(), null);
        }
    }
}