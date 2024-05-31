package api.article.jobPostingArticle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class JobDetailDto {
    private String walkingLocation;
    private LocalDateTime walkingDateTime;
    private Integer walkingMinutes;
    private Integer hourlyRate;
    private String additionalRequirements;

    public JobDetailDto(String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate, String additionalRequirements) {
        this.walkingLocation = walkingLocation;
        this.walkingDateTime = walkingDateTime;
        this.walkingMinutes = walkingMinutes;
        this.hourlyRate = hourlyRate;
        this.additionalRequirements = additionalRequirements;
    }


}
