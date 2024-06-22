package api.user.userAccount;

import api.common.util.jwt.JwtTestUtil;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.dto.UserAccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    private UserAccountDto userAccountDto;

    @BeforeEach
    void setUp() {
        userAccountDto = new UserAccountDto();
        userAccountDto.setEmail("test@example.com");
        userAccountDto.setUserName("Test User");
        userAccountDto.setRole(Role.OWNER);
        userAccountDto.setContact("1234567890");
        userAccountDto.setGender(Gender.MALE);
        userAccountDto.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("모든 기준으로 user account 찾기 테스트")
    void testFindAllUsers() throws Exception {
        // Given
        when(userAccountService.findUsersByAllCriteria(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(userAccountDto));

        // When & Then
        mockMvc.perform(get("/api/user/account")
                        .param("id", "1")
                        .param("email", "test@example.com")
                        .param("userName", "Test User")
                        .param("role", Role.OWNER.toString())
                        .param("contact", "1234567890")
                        .param("gender", Gender.MALE.toString())
                        .param("birthday", "1990-01-01")
                        .with(JwtTestUtil.mockUser("admin", "ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All users found successfully"))
                .andExpect(jsonPath("$.data[0].email").value("test@example.com"))
                .andExpect(jsonPath("$.data[0].userName").value("Test User"));

        verify(userAccountService, times(1)).findUsersByAllCriteria(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("user account 삭제 테스트")
    void testDeleteUserById() throws Exception {
        // Given
        doNothing().when(userAccountService).deleteUserById(anyInt());

        // When & Then
        mockMvc.perform(delete("/api/user/account/1")
                        .with(JwtTestUtil.mockUser("admin", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));

        verify(userAccountService, times(1)).deleteUserById(1);
    }
}