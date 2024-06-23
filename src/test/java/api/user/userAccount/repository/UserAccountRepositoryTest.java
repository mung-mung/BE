package api.user.userAccount.repository;

import api.common.config.repository.RepositoryConfig;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.userAccount.dto.UserAccountDto;
import api.user.walker.Walker;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({UserAccountRepositoryTest.TestConfig.class, RepositoryConfig.class})
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Test
    @DisplayName("이메일로 user account 검색")
    public void testFindByEmail() {
        // given
        Owner owner = new Owner("test@example.com", "testuser", Role.OWNER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        userAccountRepository.save(owner);

        // when
        Optional<Owner> foundUser = userAccountRepository.findByEmail("test@example.com").map(Owner.class::cast);

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("userName으로 user account 검색")
    public void testFindByUserName() {
        // given
        Walker walker = new Walker("walker@example.com", "testwalker", Role.WALKER, "password", "123-456-7890", Gender.FEMALE, LocalDate.of(1990, 1, 1));
        userAccountRepository.save(walker);

        // when
        Optional<Walker> foundUser = userAccountRepository.findByUserName("testwalker").map(Walker.class::cast);

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo("testwalker");
    }

    @Test
    @DisplayName("모든 조건으로 user account 검색")
    public void testFindUsersByAllCriteria() {
        // given
        Owner owner = new Owner("owner@example.com", "testowner", Role.OWNER, "password", "321-654-0987", Gender.MALE, LocalDate.of(1990, 1, 1));
        userAccountRepository.save(owner);

        // when
        List<UserAccountDto> foundUsers = userAccountRepository.findUsersByAllCriteria(null, "owner@example.com", "testowner", Role.OWNER, "321-654-0987", Gender.MALE, LocalDate.of(1990, 1, 1));

        // then
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.get(0).getEmail()).isEqualTo("owner@example.com");
        assertThat(foundUsers.get(0).getUserName()).isEqualTo("testowner");
        assertThat(foundUsers.get(0).getContact()).isEqualTo("321-654-0987");
        assertThat(foundUsers.get(0).getGender()).isEqualTo(Gender.MALE);
        assertThat(foundUsers.get(0).getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }
}