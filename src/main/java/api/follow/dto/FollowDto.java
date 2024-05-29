package api.follow.dto;

import api.follow.Follow;
import api.user.userAccount.UserAccount;
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
        this.followerId = follow.getFollower().getId();
        this.followeeId = follow.getFollowee().getId();
    }

}
