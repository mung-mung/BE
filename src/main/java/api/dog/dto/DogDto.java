package api.dog.dto;

import api.dog.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class DogDto {
    private Integer id;
    private String name;
    private LocalDate birthday;
    private String breed;
    private Float weight;
    private Sex sex;
    public DogDto(Integer id, String name, LocalDate birthday, String breed, Float weight, Sex sex) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.weight = weight;
        this.sex = sex;
    }
}