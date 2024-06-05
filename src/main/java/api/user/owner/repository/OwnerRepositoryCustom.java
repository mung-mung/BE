package api.user.owner.repository;

import api.user.owner.dto.OwnerDto;
import api.user.enums.Gender;
import api.user.owner.dto.OwningDogsDto;

import java.time.LocalDate;
import java.util.List;

public interface OwnerRepositoryCustom {
    List<OwnerDto> findOwnersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday);
    List<OwningDogsDto> findAllOwningDogs(Integer ownerId);
}
