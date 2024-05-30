package api.follow;

import api.user.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name="follow")
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

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Follow(UserAccount follower, UserAccount followee){
        this.follower = follower;
        this.followee = followee;
    }
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }
}
