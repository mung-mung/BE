package api.owning.dto;

import api.owning.Owning;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public OwningDto(Integer id, Integer ownerId, Integer dogId) {
        this.id = id;
        this.ownerId = ownerId;
        this.dogId = dogId;
    }
}
