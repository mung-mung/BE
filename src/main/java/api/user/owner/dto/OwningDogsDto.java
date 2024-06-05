package api.user.owner.dto;

import api.dog.dto.DogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Setter
@Getter
public class OwningDogsDto {
    private Integer ownerId;
    private List<DogDto> owningDogs;
    public OwningDogsDto(Integer ownerId, List<DogDto> owningDogs) {
        this.ownerId = ownerId;
        this.owningDogs = owningDogs;
    }
}