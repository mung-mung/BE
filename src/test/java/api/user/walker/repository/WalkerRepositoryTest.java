package api.user.walker.repository;

import api.common.config.repository.RepositoryConfig;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.walker.Walker;
import api.user.walker.dto.WalkerDto;
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
    @DisplayName("param으로 들어온 모든 조건으로 walker 검색")
    public void testFindWalkersByAllCriteria() {
        // given
        Walker walker = new Walker("test@example.com", "testwalker", Role.WALKER, "password", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));
        walkerRepository.save(walker);

        // when
        List<WalkerDto> foundWalkers = walkerRepository.findWalkersByAllCriteria(null, "test@example.com", "testwalker", "123-456-7890", Gender.MALE, LocalDate.of(1990, 1, 1));

        // then
        assertThat(foundWalkers).isNotEmpty();
        assertThat(foundWalkers.get(0).getEmail()).isEqualTo("test@example.com");
        assertThat(foundWalkers.get(0).getUserName()).isEqualTo("testwalker");
        assertThat(foundWalkers.get(0).getContact()).isEqualTo("123-456-7890");
        assertThat(foundWalkers.get(0).getGender()).isEqualTo(Gender.MALE);
        assertThat(foundWalkers.get(0).getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("userName으로 walker 검색")
    public void testFindByUserName() {
        // given
        Walker walker = new Walker("test2@example.com", "testwalker2", Role.WALKER, "password", "123-456-7891", Gender.FEMALE, LocalDate.of(1991, 2, 2));
        walkerRepository.save(walker);

        // when
        List<WalkerDto> foundWalkers = walkerRepository.findWalkersByAllCriteria(null, null, "testwalker2", null, null, null);

        // then
        assertThat(foundWalkers).isNotEmpty();
        assertThat(foundWalkers.get(0).getUserName()).isEqualTo("testwalker2");
    }

    @Test
    @DisplayName("성별로 walker 검색")
    public void testFindByGender() {
        // given
        Walker walker1 = new Walker("test1@example.com", "testwalker1", Role.WALKER, "password", "123-456-7891", Gender.FEMALE, LocalDate.of(1991, 2, 2));
        Walker walker2 = new Walker("test2@example.com", "testwalker2", Role.WALKER, "password", "123-456-7892", Gender.MALE, LocalDate.of(1992, 3, 3));
        walkerRepository.save(walker1);
        walkerRepository.save(walker2);

        // when
        List<WalkerDto> foundMaleWalkers = walkerRepository.findWalkersByAllCriteria(null, null, null, null, Gender.MALE, null);

        // then
        assertThat(foundMaleWalkers).isNotEmpty();
        assertThat(foundMaleWalkers.get(0).getGender()).isEqualTo(Gender.MALE);
    }
}