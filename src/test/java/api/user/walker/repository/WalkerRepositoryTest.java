package api.user.walker.repository;

import api.common.config.repository.QueryDslConfig;
import api.common.config.repository.RepositoryConfig;
import api.user.walker.Walker;
import api.user.enums.Gender;
import api.user.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, RepositoryConfig.class})
public class WalkerRepositoryTest {

    @Autowired
    private WalkerRepository walkerRepository;

    @Test
    @DisplayName("이메일로 walker 검색")
    public void testFindByEmail() {
        // given
        Walker walker = new Walker("test@example.com", "testwalker", Role.WALKER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        walkerRepository.save(walker);

        // when
        Optional<Walker> foundWalker = walkerRepository.findByEmail("test@example.com");

        // then
        assertThat(foundWalker).isPresent();
        assertThat(foundWalker.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("모든 조건으로 walker 검색")
    public void testFindWalkersByAllCriteria() {
        // given
        Walker walker1 = new Walker("test1@example.com", "testwalker1", Role.WALKER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        Walker walker2 = new Walker("test2@example.com", "testwalker2", Role.WALKER, "password", "123-456-7890", Gender.FEMALE, LocalDate.of(1992, 5, 15));
        walkerRepository.save(walker1);
        walkerRepository.save(walker2);

        // when
        List<Walker> walkers = walkerRepository.findAll();

        // then
        assertThat(walkers).hasSize(2);
        assertThat(walkers).extracting(Walker::getEmail).containsExactlyInAnyOrder("test1@example.com", "test2@example.com");
    }
}