package api.user.walker;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.walker.dto.WalkerDto;
import api.user.walker.repository.WalkerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalkerServiceTest {

    @Mock
    private WalkerRepository walkerRepository;

    @InjectMocks
    private WalkerService walkerService;

    @Test
    @DisplayName("모든 조건으로 walker 검색")
    void testFindWalkersByAllCriteria() {
        // Given
        Integer id = 1;
        String email = "test@example.com";
        String userName = "Test Walker";
        String contact = "1234567890";
        Gender gender = Gender.MALE;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        Walker walker = new Walker(email, userName, Role.WALKER, "password", contact, gender, birthday);
        List<WalkerDto> expectedWalkers = Collections.singletonList(new WalkerDto(walker));

        when(walkerRepository.findWalkersByAllCriteria(id, email, userName, contact, gender, birthday))
                .thenReturn(expectedWalkers);

        // When
        List<WalkerDto> result = walkerService.findWalkersByAllCriteria(id, email, userName, contact, gender, birthday);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo(email);
        assertThat(result.get(0).getUserName()).isEqualTo(userName);

        verify(walkerRepository, times(1)).findWalkersByAllCriteria(id, email, userName, contact, gender, birthday);
    }
}