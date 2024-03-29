package api.owning.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class OwningDto {
    private Integer ownerId;
    private Integer dogId;
    public OwningDto(Integer ownerId, Integer dogId){
        this.ownerId = ownerId;
        this.dogId = dogId;
    }
}
