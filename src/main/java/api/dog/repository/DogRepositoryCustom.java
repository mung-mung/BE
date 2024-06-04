package api.dog.repository;

import api.dog.dto.DogDto;
import api.dog.enums.Sex;

import java.time.LocalDate;
import java.util.List;

public interface DogRepositoryCustom {
    List<DogDto> findDogsByAllCriteria(Integer Id, String name, LocalDate birthday, String Breed, Float weight, Sex sex);
}
