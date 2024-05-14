package api.follow;

import api.follow.dto.FollowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Transactional
    public Follow createFollow(FollowDto followDto) {
        Follow follow = new Follow(followDto.getFollowerId(), followDto.getFolloweeId());
        return followRepository.save(follow);
    }

    @Transactional(readOnly = true)
    public List<Follow> findAll() {

    }

    @Transactional(readOnly = true)
    public Follow findByfollowerId(int followerid) {

    }

    @Transactional(readOnly = true)
    public Follow findByfolloweeId(int followeeid) {

    }

    @Transactional
    public Follow deleteFollow(Integer followeeid) {

    }
}
