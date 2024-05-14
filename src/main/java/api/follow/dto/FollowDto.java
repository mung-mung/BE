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

    public FollowDto(Integer id, Integer followerId, Integer followeeId){
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public FollowDto(Integer followerId, Integer followeeId){
        this.id = null;
    }
}
