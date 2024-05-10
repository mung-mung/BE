package api.owning.dto;

import api.dog.Dog;
import api.owning.Owning;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Setter
@Getter
public class OwningDto {
    private Integer id;
    private Integer ownerId;
    private Integer dogId;
    public OwningDto(Owning owning){
        this.id = owning.getId();
        this.ownerId = owning.getOwner().getId();
        this.dogId = owning.getDog().getId();
    }
}
