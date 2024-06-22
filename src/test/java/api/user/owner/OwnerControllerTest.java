package api.user.owner;

import api.common.util.jwt.JwtTestUtil;
import api.user.enums.Gender;
import api.user.owner.dto.OwnerDto;
import api.user.owner.dto.OwningDogsDto;
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

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    private OwnerDto ownerDto;
    private OwningDogsDto owningDogsDto;

    @BeforeEach
    void setUp() {
        ownerDto = new OwnerDto();
        ownerDto.setEmail("test@example.com");
        ownerDto.setUserName("Test Owner");
        ownerDto.setContact("1234567890");
        ownerDto.setGender(Gender.MALE);
        ownerDto.setBirthday(LocalDate.of(1990, 1, 1));

        owningDogsDto = new OwningDogsDto();
    }

    @Test
    @DisplayName("모든 기준으로 owner 찾기 테스트")
    void testFindAllOwners() throws Exception {
        // Given
        when(ownerService.findOwnersByAllCriteria(any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(ownerDto));

        // When & Then
        mockMvc.perform(get("/api/user/owner")
                        .param("id", "1")
                        .param("email", "test@example.com")
                        .param("userName", "Test Owner")
                        .param("contact", "1234567890")
                        .param("gender", Gender.MALE.toString())
                        .param("birthday", "1990-01-01")
                        .with(JwtTestUtil.mockUser("admin", "ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All owners found successfully"))
                .andExpect(jsonPath("$.data[0].email").value("test@example.com"))
                .andExpect(jsonPath("$.data[0].userName").value("Test Owner"));

        verify(ownerService, times(1)).findOwnersByAllCriteria(any(), any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("모든 dog 찾기 테스트 - 성공")
    void testFindAllOwningDogs_Success() throws Exception {
        // Given
        when(ownerService.findAllOwningDogs()).thenReturn(Collections.singletonList(owningDogsDto));

        // When & Then
        mockMvc.perform(get("/api/user/owner/dogs")
                        .with(JwtTestUtil.mockUser("owner", "OWNER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All dogs found successfully"))
                .andExpect(jsonPath("$.data").isArray());

        verify(ownerService, times(1)).findAllOwningDogs();
    }

    @Test
    @DisplayName("모든 dog 찾기 테스트 - 접근 거부")
    void testFindAllOwningDogs_AccessDenied() throws Exception {
        // Given
        doThrow(new AccessDeniedException("Only owners can find their owning dogs."))
                .when(ownerService).findAllOwningDogs();

        // When & Then
        mockMvc.perform(get("/api/user/owner/dogs")
                        .with(JwtTestUtil.mockUser("walker", "WALKER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Only owners can find their owning dogs."));

        verify(ownerService, times(1)).findAllOwningDogs();
    }
}