package api.user.walker.repository;

import api.user.walker.dto.WalkerDto;
import api.user.enums.Gender;

import java.time.LocalDate;
import java.util.List;

public interface WalkerRepositoryCustom {
    List<WalkerDto> findWalkersByAllCriteria(Integer id, String email, String userName, String contact, Gender gender, LocalDate birthday);

}
