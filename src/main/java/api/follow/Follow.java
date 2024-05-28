package api.follow;

import api.user.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWER_ID", nullable = false)
    private UserAccount follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWEE_ID", nullable = false)
    private UserAccount followee;

    public Follow(UserAccount follower, UserAccount followee){
        this.follower = follower;
        this.followee = followee;
    }
}
