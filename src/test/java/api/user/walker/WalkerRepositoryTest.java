package api.user.walker;

import api.user.enums.Gender;
import api.user.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WalkerRepositoryTest {
    @Autowired
    private WalkerRepository walkerRepository;

    private Walker createWalker() {
        return new Walker("walker@example.com", Role.WALKER, "password", "123456789", Gender.MALE, LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("walker 저장 테스트")
    void TestSaveAndFindById() {
        Walker savedWalker = walkerRepository.save(createWalker());
        Optional<Walker> foundWalker = walkerRepository.findById(savedWalker.getId());
        assertTrue(foundWalker.isPresent());
        assertThat(foundWalker.get().getEmail()).isEqualTo("walker@example.com");
    }

    @Test
    @DisplayName("모든 유저 조회")
    void TestFindAll() {
        walkerRepository.save(createWalker());
        walkerRepository.save(new Walker("Walker2@test.com", Role.WALKER, "testpass2", "0987654321", Gender.FEMALE, LocalDate.of(1995, 5, 15)));
        List<Walker> Walkers = walkerRepository.findAll();
        assertThat(Walkers.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("id로 Walker 삭제 테스트")
    void TestDeleteById() {
        Walker savedWalker = walkerRepository.save(createWalker());
        Integer id = savedWalker.getId();
        walkerRepository.deleteById(id);
        Optional<Walker> foundWalker = walkerRepository.findById(id);
        assertFalse(foundWalker.isPresent());
    }

    @Test
    @DisplayName("Walker 수 조회 테스트")
    void TestCount() {
        walkerRepository.save(createWalker());

        long count = walkerRepository.count();
        assertTrue(count >= 1);
    }
}
