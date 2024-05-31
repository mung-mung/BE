package api.article.jobPostingArticle;

import api.article.jobPostingArticle.dto.CreateJobPostingDto;
import api.article.jobPostingArticle.dto.JobDetailDto;
import api.article.jobPostingArticle.dto.JobPostingArticleDto;
import api.common.util.http.HttpResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/article/job-posting")
public class JobPostingArticleController {

    private final JobPostingArticleService jobPostingArticleService;

    public JobPostingArticleController(JobPostingArticleService jobPostingArticleService) {
        this.jobPostingArticleService = jobPostingArticleService;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> getAllJobPostingArticles(){
        try {
            return HttpResponse.successOk("All job posting articles found successfully", jobPostingArticleService.getAllArticles());
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to find job posting articles: " + e.getMessage(), null);
        }
    }

    @PostMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> createJobPostingArticle(@RequestBody CreateJobPostingDto createJobPostingDto) {
        try {
            JobPostingArticleDto createdArticleDto = jobPostingArticleService.createArticle(createJobPostingDto);
            return HttpResponse.successCreated("Job posting article successfully created.", createdArticleDto);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating job posting article: " + e.getMessage(), null);
        }
    }

    @PatchMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> updateJobDetail(@PathVariable Integer articleId, @RequestBody JobDetailDto jobDetailDto) {
        try {
            JobPostingArticleDto updatedJobPostingArticleDto = jobPostingArticleService.updateArticleContractDetail(articleId, jobDetailDto);
            if (updatedJobPostingArticleDto != null) {
                return HttpResponse.successOk("Job details of the job posting article updated successfully", updatedJobPostingArticleDto);
            } else {
                return HttpResponse.notFound("Job posting article not found", null);
            }
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error updating job details: " + e.getMessage(), null);
        }
    }


    @DeleteMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> deleteJobPostingArticle(@PathVariable Integer articleId) {
        try {
            jobPostingArticleService.deleteArticle(articleId);
            return HttpResponse.successOk("Job posting article deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting job posting article: " + e.getMessage(), null);
        }
    }

}
