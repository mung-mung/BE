package api.user.userAccount;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.userAccount.dto.UserAccountDto;
import api.user.userAccount.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    @DisplayName("이메일로 user 검색")
    void testFindUserByEmail() {
        // Given
        String email = "test@example.com";
        Owner owner = new Owner(email, "Test User", Role.OWNER, "password", "1234567890", Gender.MALE, LocalDate.of(1990, 1, 1));
        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(owner));

        // When
        UserAccountDto userAccountDto = userAccountService.findUserByEmail(email);

        // Then
        assertThat(userAccountDto).isNotNull();
        assertThat(userAccountDto.getEmail()).isEqualTo(email);
        assertThat(userAccountDto.getUserName()).isEqualTo("Test User");

        verify(userAccountRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Id로 user 삭제")
    void testDeleteUserById() {
        // Given
        Integer userId = 1;
        doNothing().when(userAccountRepository).deleteById(userId);

        // When
        userAccountService.deleteUserById(userId);

        // Then
        verify(userAccountRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("모든 조건으로 user 검색")
    void testFindUsersByAllCriteria() {
        Integer id = 1;
        String email = "test@example.com";
        String userName = "Test User";
        Role role = Role.OWNER;
        String contact = "1234567890";
        Gender gender = Gender.MALE;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        // Define expected behavior for repository method
        when(userAccountRepository.findUsersByAllCriteria(id, email, userName, role, contact, gender, birthday))
                .thenReturn(Collections.emptyList());

        // Call the service method
        List<UserAccountDto> result = userAccountService.findUsersByAllCriteria(id, email, userName, role, contact, gender, birthday);

        // Assert that the result is an empty list
        assertThat(result).isEmpty();

        // Verify that the repository method was called with the correct arguments
        verify(userAccountRepository, times(1)).findUsersByAllCriteria(id, email, userName, role, contact, gender, birthday);
    }
}