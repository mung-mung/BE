package api.article.jobPostingArticle;

import api.article.article.Article;
import api.article.enums.ArticleTypes;
import api.article.jobPostingArticle.dto.JobDetailDto;
import api.owning.Owning;
import api.user.owner.Owner;
import api.user.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Getter
@DiscriminatorValue("JOB_POSTING")
@Entity
@Table(name="job_posting_article")
public class JobPostingArticle extends Article {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNING_ID", nullable = false)
    private Owning owning;

    @Column(name = "WALKING_LOCATION", nullable = false)
    private String walkingLocation;

    @Column(name = "WALKING_DATETIME", nullable = false)
    private LocalDateTime walkingDateTime;

    @Column(name = "WALKING_MIN", nullable = false)
    private Integer walkingMinutes;

    @Column(name = "HOURLY_RATE", nullable = false)
    private Integer hourlyRate;

    @Column(name = "ADDITIONAL_REQUIREMENTS", nullable = true)
    private String additionalRequirements;

    public JobPostingArticle(UserAccount writer, Owning owning, String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate, String additionalRequirements){
        super(writer, ArticleTypes.JOB_POSTING);
        this.owning = owning;
        this.walkingLocation = walkingLocation;
        this.walkingDateTime = walkingDateTime;
        this.walkingMinutes = walkingMinutes;
        this.hourlyRate = hourlyRate;
        this.additionalRequirements = additionalRequirements;
    }

    public JobPostingArticle(Owning owning, JobDetailDto jobDetailDto) {
        super(owning.getOwner(), ArticleTypes.JOB_POSTING);
        this.owning = owning;
        this.walkingLocation = jobDetailDto.getWalkingLocation();
        this.walkingDateTime = jobDetailDto.getWalkingDateTime();
        this.walkingMinutes = jobDetailDto.getWalkingMinutes();
        this.hourlyRate = jobDetailDto.getHourlyRate();
        this.additionalRequirements = jobDetailDto.getAdditionalRequirements();
    }

    public JobPostingArticle updateJobDetail(JobDetailDto jobDetailDto){
        this.walkingLocation = jobDetailDto.getWalkingLocation();
        this.walkingDateTime = jobDetailDto.getWalkingDateTime();
        this.walkingMinutes = jobDetailDto.getWalkingMinutes();
        this.hourlyRate = jobDetailDto.getHourlyRate();
        this.additionalRequirements = jobDetailDto.getAdditionalRequirements();
        return this;
    }
}
