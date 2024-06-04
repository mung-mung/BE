package api.article.jobPostingArticle.dto;

import api.article.jobPostingArticle.JobPostingArticle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class JobPostingArticleDto {
    private Integer id;
    private Integer writerId;
    private Integer ownerId;
    private Integer dogId;
    private JobDetailDto jobDetailDto;
    public JobPostingArticleDto(Integer id, Integer writerId, Integer ownerId, Integer dogId, String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate, String additionalRequirements){
        this.id = id;
        this.writerId = writerId;
        this.ownerId = ownerId;
        this.dogId = dogId;
        this.jobDetailDto = new JobDetailDto(walkingLocation, walkingDateTime, walkingMinutes, hourlyRate, additionalRequirements);
    }

    public JobPostingArticleDto(JobPostingArticle jobPostingArticle) {
        this.id = jobPostingArticle.getId();
        this.writerId = jobPostingArticle.getWriter().getId();
        this.ownerId = jobPostingArticle.getOwning().getOwner().getId();
        this.dogId = jobPostingArticle.getOwning().getDog().getId();
        this.jobDetailDto = new JobDetailDto(
                jobPostingArticle.getWalkingLocation(),
                jobPostingArticle.getWalkingDateTime(),
                jobPostingArticle.getWalkingMinutes(),
                jobPostingArticle.getHourlyRate(),
                jobPostingArticle.getAdditionalRequirements());
    }

}