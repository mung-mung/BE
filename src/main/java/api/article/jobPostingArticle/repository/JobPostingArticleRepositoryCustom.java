package api.article.jobPostingArticle.repository;

import api.article.jobPostingArticle.dto.JobPostingArticleDto;

import java.time.LocalDateTime;
import java.util.List;


public interface JobPostingArticleRepositoryCustom {
    List<JobPostingArticleDto> findJobPostingArticlesByAllCriteria(Integer id, Integer writerId, Integer ownerId, Integer dogId, String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate);
}