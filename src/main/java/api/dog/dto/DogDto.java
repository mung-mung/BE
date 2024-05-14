package api.dog.dto;

import api.dog.Dog;
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
    private String sex;
    public DogDto(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.birthday = dog.getBirthday();
        this.breed = dog.getBreed();
        this.weight = dog.getWeight();
        this.sex = dog.getSex().toString();
    }
}