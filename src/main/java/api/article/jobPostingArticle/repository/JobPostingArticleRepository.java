package api.article.jobPostingArticle.repository;

import api.article.jobPostingArticle.JobPostingArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingArticleRepository extends JpaRepository<JobPostingArticle, Integer>, JobPostingArticleRepositoryCustom {
}
