package api.common.config.repository;

import api.user.userAccount.repository.UserAccountRepositoryCustomImpl;
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
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
    @Bean
    public UserAccountRepositoryCustomImpl userAccountRepositoryCustom(JPAQueryFactory queryFactory) {
        return new UserAccountRepositoryCustomImpl(queryFactory);
    }
}
