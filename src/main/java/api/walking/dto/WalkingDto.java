package api.walking.dto;

import api.walking.Walking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WalkingDto {
    private Integer id;
    private Integer walkerId;
    private Integer dogId;
    public WalkingDto(Walking walking){
        this.id = walking.getId();
        this.walkerId = walking.getWalker().getId();
        this.dogId = walking.getDog().getId();
    }
}
