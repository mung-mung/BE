package api.user.owner;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.dto.OwnerDto;
import api.user.owner.dto.OwningDogsDto;
import api.user.owner.repository.OwnerRepository;
import api.user.userAccount.dto.UserAccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;
    private UserAccountDto loggedInUserAccountDto;
    private Owner walker;
    private UserAccountDto loggedInWalkerAccountDto;

    @BeforeEach
    void setUp() {
        owner = new Owner("test@example.com", "Test Owner", Role.OWNER, "password", "1234567890", Gender.MALE, LocalDate.of(1990, 1, 1));
        loggedInUserAccountDto = new UserAccountDto(owner);

        walker = new Owner("walker@example.com", "Test Walker", Role.WALKER, "password", "1234567890", Gender.FEMALE, LocalDate.of(1992, 2, 2));
        loggedInWalkerAccountDto = new UserAccountDto(walker);
    }

    @Test
    @DisplayName("모든 기준으로 owner 찾기 테스트")
    void testFindOwnersByAllCriteria() {
        // Given
        Integer id = 1;
        String email = "test@example.com";
        String userName = "Test Owner";
        String contact = "1234567890";
        Gender gender = Gender.MALE;
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        List<OwnerDto> expectedOwners = Collections.singletonList(new OwnerDto(owner));

        when(ownerRepository.findOwnersByAllCriteria(id, email, userName, contact, gender, birthday))
                .thenReturn(expectedOwners);

        // When
        List<OwnerDto> result = ownerService.findOwnersByAllCriteria(id, email, userName, contact, gender, birthday);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo(email);
        assertThat(result.get(0).getUserName()).isEqualTo(userName);

        verify(ownerRepository, times(1)).findOwnersByAllCriteria(id, email, userName, contact, gender, birthday);
    }

    @Test
    @DisplayName("로그인된 사용자가 없는 경우 접근 거부 테스트")
    void testFindAllOwningDogs_AccessDenied_NoLoggedInUser() {
        try (MockedStatic<LoggedInUser> mockedStatic = mockStatic(LoggedInUser.class)) {
            // Given
            mockedStatic.when(LoggedInUser::getLoggedInUserAccountDto).thenReturn(null);

            // When & Then
            assertThatThrownBy(() -> ownerService.findAllOwningDogs())
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessage("Only owners can find their owning dogs.");

            mockedStatic.verify(LoggedInUser::getLoggedInUserAccountDto, times(1));
        }
    }

    @Test
    @DisplayName("로그인된 사용자가 owner가 아닌 경우 접근 거부 테스트")
    void testFindAllOwningDogs_AccessDenied_NotOwner() {
        try (MockedStatic<LoggedInUser> mockedStatic = mockStatic(LoggedInUser.class)) {
            // Given
            mockedStatic.when(LoggedInUser::getLoggedInUserAccountDto).thenReturn(loggedInWalkerAccountDto);

            // When & Then
            assertThatThrownBy(() -> ownerService.findAllOwningDogs())
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessage("Only owners can find their owning dogs.");

            mockedStatic.verify(LoggedInUser::getLoggedInUserAccountDto, times(1));
        }
    }

    @Test
    @DisplayName("유효하지 않은 owner 계정으로 접근 시 접근 거부 테스트")
    void testFindAllOwningDogs_InvalidOwner() {
        try (MockedStatic<LoggedInUser> mockedStatic = mockStatic(LoggedInUser.class)) {
            // Given
            mockedStatic.when(LoggedInUser::getLoggedInUserAccountDto).thenReturn(loggedInUserAccountDto);
            when(ownerRepository.findByEmail(loggedInUserAccountDto.getEmail())).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> ownerService.findAllOwningDogs())
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessage("Invalid owner account");

            mockedStatic.verify(LoggedInUser::getLoggedInUserAccountDto, times(1));
            verify(ownerRepository, times(1)).findByEmail(loggedInUserAccountDto.getEmail());
        }
    }

    @Test
    @DisplayName("정상적으로 owner의 모든 dog를 찾는 테스트")
    void testFindAllOwningDogs_Success() throws AccessDeniedException {
        try (MockedStatic<LoggedInUser> mockedStatic = mockStatic(LoggedInUser.class)) {
            // Given
            mockedStatic.when(LoggedInUser::getLoggedInUserAccountDto).thenReturn(loggedInUserAccountDto);
            when(ownerRepository.findByEmail(loggedInUserAccountDto.getEmail())).thenReturn(Optional.of(owner));
            List<OwningDogsDto> owningDogs = Collections.singletonList(new OwningDogsDto());
            when(ownerRepository.findAllOwningDogs(owner.getId())).thenReturn(owningDogs);

            // When
            List<OwningDogsDto> result = ownerService.findAllOwningDogs();

            // Then
            assertThat(result).isNotNull();
            assertThat(result).hasSize(1);

            mockedStatic.verify(LoggedInUser::getLoggedInUserAccountDto, times(1));
            verify(ownerRepository, times(1)).findByEmail(loggedInUserAccountDto.getEmail());
            verify(ownerRepository, times(1)).findAllOwningDogs(owner.getId());
        }
    }
}