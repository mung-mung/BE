package api.common.config.repository;

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
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
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
}
