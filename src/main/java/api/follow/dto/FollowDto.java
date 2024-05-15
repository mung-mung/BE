package api.follow.dto;

import api.follow.Follow;
import api.user.dto.UserDtoAbstractClass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FollowDto{
    private Integer id;
    private Integer followerId;
    private Integer followeeId;

    public FollowDto(Follow follow) {
        this.id = follow.getId();
        this.followerId = follow.getFollowerId();
        this.followeeId = follow.getFolloweeId();
    }

    public FollowDto(Integer followerId, Integer followeeId){
        this.id = null;
    }
    
}
