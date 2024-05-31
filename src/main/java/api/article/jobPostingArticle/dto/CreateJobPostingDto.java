package api.article.jobPostingArticle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateJobPostingDto {
    Integer dogId;
    JobDetailDto jobDetailDto;

    public CreateJobPostingDto(Integer dogId, JobDetailDto jobDetailDto) {
        this.dogId = dogId;
        this.jobDetailDto = jobDetailDto;
    }
}
