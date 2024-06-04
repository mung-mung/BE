package api.user.owner.repository;

import api.user.dto.OwnerDto;
import api.user.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public interface OwnerRepositoryCustom {
    List<OwnerDto> findOwnersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday);

}
