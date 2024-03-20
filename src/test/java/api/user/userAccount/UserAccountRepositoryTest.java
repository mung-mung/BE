package api.user.userAccount;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.walker.Walker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class UserAccountRepositoryTest {
    @Autowired
    private UserAccountRepository userAccountRepository;

    private Owner createOwner() {
        return new Owner("owner@example.com", Role.OWNER, "password", "123456789", Gender.MALE, LocalDate.of(1990, 1, 1));
    }

    private Walker createWalker() {
        return new Walker("walker@example.com", Role.WALKER, "password", "987654321", Gender.FEMALE, LocalDate.of(1995, 5, 15));
    }

    @Test
    @DisplayName("모든 유저 조회")
    public void testFindAll() {
        Owner owner = createOwner();
        Walker walker = createWalker();
        userAccountRepository.save(owner);
        userAccountRepository.save(walker);
        List<UserAccount> users = userAccountRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Id로 유저 삭제")
    public void testDeleteById() {
        Owner owner = createOwner();
        owner = userAccountRepository.save(owner);
        userAccountRepository.deleteById(owner.getId());
        Optional<UserAccount> deletedUser = userAccountRepository.findById(owner.getId());
        assertThat(deletedUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 수 조회")
    public void testCount() {
        Owner owner = createOwner();
        Walker walker = createWalker();
        userAccountRepository.save(owner);
        userAccountRepository.save(walker);
        long userCount = userAccountRepository.count();
        assertThat(userCount).isEqualTo(2);
    }
    @Test
    @DisplayName("이메일로 유저 조회")
    void testFindByEmail() {
        Owner owner = createOwner();
        userAccountRepository.save(owner);
        Optional<UserAccount> foundUser = userAccountRepository.findByEmail("owner@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("owner@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Role로 유저 조희")
    void testFindByRole() {
        Owner owner = createOwner();
        Walker walker = createWalker();
        userAccountRepository.save(owner);
        userAccountRepository.save(walker);
        List<UserAccount> owners = userAccountRepository.findByRole(Role.OWNER);
        assertNotNull(owners);
        assertFalse(owners.isEmpty());
        assertEquals(1, owners.size());
        assertEquals(Role.OWNER, owners.get(0).getRole());
        List<UserAccount> walkers = userAccountRepository.findByRole(Role.WALKER);
        assertNotNull(walkers);
        assertFalse(walkers.isEmpty());
        assertEquals(1, walkers.size());
        assertEquals(Role.WALKER, walkers.get(0).getRole());
    }
}