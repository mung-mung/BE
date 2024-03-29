package api.walking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class WalkingDto {
    private Integer walkerId;
    private Integer dogId;
    public WalkingDto(Integer walkerId, Integer dogId){
        this.walkerId = walkerId;
        this.dogId = dogId;
    }
}
