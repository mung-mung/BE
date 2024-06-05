package api.user.userAccount.repository;

import api.user.userAccount.dto.UserAccountDto;
import api.user.enums.Gender;
import api.user.enums.Role;

import java.time.LocalDate;
import java.util.List;

public interface UserAccountRepositoryCustom {
    List<UserAccountDto> findUsersByAllCriteria(Integer id, String email, String userName, Role role, String contact, Gender gender, LocalDate birthday);
}
