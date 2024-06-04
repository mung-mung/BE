package api.article.jobPostingArticle.repository;

import api.article.jobPostingArticle.QJobPostingArticle;
import api.article.jobPostingArticle.dto.JobPostingArticleDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;

public class JobPostingArticleRepositoryCustomImpl implements JobPostingArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public JobPostingArticleRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<JobPostingArticleDto> findJobPostingArticlesByAllCriteria(Integer id, Integer writerId, Integer ownerId, Integer dogId, String walkingLocation, LocalDateTime walkingDateTime, Integer walkingMinutes, Integer hourlyRate) {
        QJobPostingArticle jobPostingArticle = QJobPostingArticle.jobPostingArticle;
        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(id).ifPresent(value -> whereClause.and(jobPostingArticle.id.eq(value)));
        Optional.ofNullable(writerId).ifPresent(value -> whereClause.and(jobPostingArticle.writer.id.eq(value)));
        Optional.ofNullable(ownerId).ifPresent(value -> whereClause.and(jobPostingArticle.owning.owner.id.eq(value)));
        Optional.ofNullable(dogId).ifPresent(value -> whereClause.and(jobPostingArticle.owning.dog.id.eq(value)));
        Optional.ofNullable(walkingLocation).ifPresent(value -> whereClause.and(jobPostingArticle.walkingLocation.eq(value)));
        Optional.ofNullable(walkingDateTime).ifPresent(value -> whereClause.and(jobPostingArticle.walkingDateTime.eq(value)));
        Optional.ofNullable(walkingMinutes).ifPresent(value -> whereClause.and(jobPostingArticle.walkingMinutes.eq(value)));
        Optional.ofNullable(hourlyRate).ifPresent(value -> whereClause.and(jobPostingArticle.hourlyRate.eq(value)));
        return queryFactory.select(constructor(JobPostingArticleDto.class,
                        jobPostingArticle.id,
                        jobPostingArticle.writer.id,
                        jobPostingArticle.owning.owner.id,
                        jobPostingArticle.owning.dog.id,
                        jobPostingArticle.walkingLocation,
                        jobPostingArticle.walkingDateTime,
                        jobPostingArticle.walkingMinutes,
                        jobPostingArticle.hourlyRate,
                        jobPostingArticle.additionalRequirements))
                .from(jobPostingArticle)
                .where(whereClause)
                .fetch();
    }
}

