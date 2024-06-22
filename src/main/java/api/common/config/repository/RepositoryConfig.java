package api.common.config.repository;

import api.article.jobPostingArticle.repository.JobPostingArticleRepositoryCustomImpl;
import api.dog.repository.DogRepositoryCustomImpl;
import api.owning.repository.OwningRepositoryCustomImpl;
import api.user.owner.repository.OwnerRepositoryCustomImpl;
import api.user.userAccount.repository.UserAccountRepositoryCustomImpl;
import api.user.walker.repository.WalkerRepositoryCustomImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    private final EntityManager em;

    public RepositoryConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserAccountRepositoryCustomImpl userAccountRepositoryCustom(JPAQueryFactory queryFactory) {
        return new UserAccountRepositoryCustomImpl(queryFactory);
    }
    @Bean
    public WalkerRepositoryCustomImpl walkerRepositoryCustom(JPAQueryFactory queryFactory) {
        return new WalkerRepositoryCustomImpl(queryFactory);
    }
    @Bean
    public OwnerRepositoryCustomImpl ownerRepositoryCustom(JPAQueryFactory queryFactory) {
        return new OwnerRepositoryCustomImpl(queryFactory);
    }
    @Bean
    public DogRepositoryCustomImpl dogRepositoryCustom(JPAQueryFactory queryFactory) {
        return new DogRepositoryCustomImpl(queryFactory);
    }
    @Bean
    public JobPostingArticleRepositoryCustomImpl jobPostingArticleRepositoryCustom (JPAQueryFactory queryFactory) {
        return new JobPostingArticleRepositoryCustomImpl(queryFactory);
    }
    @Bean
    public OwningRepositoryCustomImpl owningRepositoryCustom (JPAQueryFactory queryFactory) {
        return new OwningRepositoryCustomImpl(queryFactory);
    }
}
