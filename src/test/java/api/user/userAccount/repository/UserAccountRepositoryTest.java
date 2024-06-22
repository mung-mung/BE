package api.user.userAccount.repository;

import api.common.config.repository.RepositoryConfig;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.userAccount.dto.UserAccountDto;
import api.user.walker.Walker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(RepositoryConfig.class)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

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
        Walker walker = new Walker("test@example.com", "testuser", Role.WALKER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        userAccountRepository.save(walker);

        // when
        Optional<Walker> foundUser = userAccountRepository.findByUserName("testuser").map(Walker.class::cast);

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("모든 조건으로 user account 검색")
    public void testFindUsersByAllCriteria() {
        // given
        Owner owner = new Owner("test@example.com", "testuser", Role.OWNER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        userAccountRepository.save(owner);

        // when
        List<UserAccountDto> foundUsers = userAccountRepository.findUsersByAllCriteria(null, "test@example.com", null, Role.OWNER, null, Gender.MALE, null);

        // then
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.get(0).getEmail()).isEqualTo("test@example.com");
    }
}