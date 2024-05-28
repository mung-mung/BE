package api.follow;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.follow.dto.FollowDto;
import api.user.dto.UserAccountDto;
import api.user.userAccount.UserAccount;
import api.user.userAccount.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, UserAccountRepository userAccountRepository) {
        this.followRepository = followRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public Follow createFollow(FollowDto followDto) {
        UserAccount follower = userAccountRepository.findById(followDto.getFollower().getId())
                .orElseThrow(() -> new EntityNotFoundException("Follower not found with ID: " + followDto.getFollower().getId()));
        UserAccount followee = userAccountRepository.findById(followDto.getFollowee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Followee not found with ID: " + followDto.getFollowee().getId()));
        Follow follow = new Follow(follower, followee);
        return followRepository.save(follow);
    }

    //id or followerid or followeeid로 검색
    @Transactional(readOnly = true)
    public List<FollowDto> findFollows(Integer id, Integer followerId, Integer followeeId) {
        List<Follow> follows = new ArrayList<>();

        if(id != null){
            Optional<Follow> follow = followRepository.findById(id);
            follows = follow.map(List::of).orElse(List.of());
        } else if(followerId != null && followeeId != null){
            Optional<Follow> optionalFollow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);
            Follow follow = optionalFollow.get();
            follows.add(follow);
        } else if(followerId != null){
            follows = followRepository.findByFollowerId(followerId);
        } else if(followeeId != null){
            follows = followRepository.findByFolloweeId(followeeId);
        } else{
            follows = followRepository.findAll();
        }
        return follows.stream().map(FollowDto::new).collect(Collectors.toList());
    }


    @Transactional
    public void deleteFollowById(Integer id) throws AccessDeniedException {
        //현재 로그인된 사용자 정보
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();

        //사용자 정보 없을 경우 AccessDeniedException
        if(loggedInUserAccountDto != null) {
            throw new AccessDeniedException("You are not logged in");
        }

        //id로 조회
        if(!followRepository.existsById(id)) {
            throw new EntityNotFoundException("Follow not found with ID: " + id);
        }

        //Follow 삭제
        followRepository.deleteById(id);
    }
}
