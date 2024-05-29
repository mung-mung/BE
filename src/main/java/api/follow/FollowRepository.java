package api.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollowerId(Integer followerId);
    List<Follow> findByFolloweeId(Integer followeeId);
    Optional<Follow> findByFollowerIdAndFolloweeId(Integer followerId, Integer followeeId);
}
