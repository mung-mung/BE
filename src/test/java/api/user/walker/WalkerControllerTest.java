package api.user.walker;

import api.common.util.jwt.JwtTestUtil;
import api.user.enums.Gender;
import api.user.walker.dto.WalkerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalkerController.class)
public class WalkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalkerService walkerService;

    private WalkerDto walkerDto;

    @BeforeEach
    void setUp() {
        walkerDto = new WalkerDto();
        walkerDto.setEmail("test@example.com");
        walkerDto.setUserName("Test Walker");
        walkerDto.setContact("1234567890");
        walkerDto.setGender(Gender.MALE);
        walkerDto.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("모든 기준으로 walker 찾기 테스트")
    void testFindAllWalkers() throws Exception {
        // Given
        when(walkerService.findWalkersByAllCriteria(any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(walkerDto));

        // When & Then
        mockMvc.perform(get("/api/user/walker")
                        .param("id", "1")
                        .param("email", "test@example.com")
                        .param("userName", "Test Walker")
                        .param("contact", "1234567890")
                        .param("gender", Gender.MALE.toString())
                        .param("birthday", "1990-01-01")
                        .with(JwtTestUtil.mockUser("admin", "ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All walkers found successfully"))
                .andExpect(jsonPath("$.data[0].email").value("test@example.com"))
                .andExpect(jsonPath("$.data[0].userName").value("Test Walker"));

        verify(walkerService, times(1)).findWalkersByAllCriteria(any(), any(), any(), any(), any(), any());
    }
}