package api.follow.dto;

import api.follow.Follow;
import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FollowDto{
    private Integer id;
    private UserAccount follower;
    private UserAccount followee;

    public FollowDto(Follow follow) {
        this.id = follow.getId();
        this.follower = follow.getFollower();
        this.followee = follow.getFollowee();
    }

    public FollowDto(Integer followerId, Integer followeeId){
        this.id = null;
    }

}
