package api.article;

import api.article.dto.ArticleDto;
import api.article.dto.CreateContractDto;
import api.common.util.http.HttpResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> getAllArticles(){
        try {
            return HttpResponse.successOk("All articles found successfully", articleService.getAllArticles());
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to fetch articles: " + e.getMessage(), null);
        }
    }


    @GetMapping("/{articleId}")
    @ResponseBody
        public ResponseEntity<Object> getArticleById(@PathVariable Integer articleId) {
        try {
            ArticleDto foundArticle = articleService.getArticleById(articleId);
            if (foundArticle != null) {
                return HttpResponse.successOk("Article found successfully", foundArticle);
            } else {
                return HttpResponse.notFound("Article not found with ID: " + articleId, null);
            }
        } catch (Exception e) {
            return HttpResponse.internalError("Error finding Article: " + e.getMessage(), null);
        }
    }

    @PostMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> createArticle(@RequestBody CreateContractDto createContractDto) {
        try {
            ArticleDto createdArticleDto = articleService.createArticle(createContractDto);
            return HttpResponse.successCreated("Article successfully created.", createdArticleDto);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating article: " + e.getMessage(), null);
        }
    }

    @PatchMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> updateArticleContractDetail(@PathVariable Integer articleId, @RequestBody ArticleContractDetail articleContractDetail) {
        try {
            ArticleDto updatedArticleDto = articleService.updateArticleContractDetail(articleId, articleContractDetail);
            if (updatedArticleDto != null) {
                return HttpResponse.successOk("Article successfully updated", updatedArticleDto);
            } else {
                return HttpResponse.notFound("Article not found", null);
            }
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error updating Article: " + e.getMessage(), null);
        }
    }


    @DeleteMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> deleteArticle(@PathVariable Integer articleId) {
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
