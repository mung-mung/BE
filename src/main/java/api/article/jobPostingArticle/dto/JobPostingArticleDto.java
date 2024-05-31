package api.article.jobPostingArticle.dto;

import api.article.jobPostingArticle.JobPostingArticle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JobPostingArticleDto {
    private Integer id;
    private Integer writerId;
    private Integer ownerId;
    private Integer dogId;
    private JobDetailDto jobDetailDto;

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