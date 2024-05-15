package api.follow;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.follow.dto.FollowDto;
import api.user.dto.UserAccountDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<FollowDto> findAllFollows() {
        List<Follow> allfollows = followRepository.findAll();
        List<FollowDto> followDtos = new ArrayList<>();
        for (Follow follow : allfollows) {
            FollowDto followDto = new FollowDto(follow.getFollowerId(), follow.getFolloweeId());
            followDtos.add(followDto);
        }
        return followDtos;
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
