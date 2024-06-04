package api.owning.repository;

import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import api.owning.dto.OwningDto;

import java.time.LocalDate;
import java.util.List;

public interface OwningRepositoryCustom {
    List<OwningDto> findOwningsByAllCriteria(Integer id, Integer ownerId, Integer dogId);

}