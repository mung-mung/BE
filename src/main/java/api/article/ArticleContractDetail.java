package api.article;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
@Embeddable
public class ArticleContractDetail {
    @Column(name = "WALKING_LOCATION", nullable = false)
    private String walkingLocation;

    @Column(name = "WALKING_DATETIME", nullable = false)
    private LocalDateTime walkingStartTime;

    @Column(name = "WALKING_DURATION", nullable = false)
    private Duration walkingDuration;

    @Column(name = "HOURLY_RATE", nullable = false)
    private Integer hourlyRate;

    @Column(name = "ADDITIONAL_REQUIREMENTS", nullable = true)
    private String additionalRequirements;
}
