package api.follow;

import api.dog.DogRepository;
import api.follow.dto.FollowDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final DogRepository dogRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, DogRepository dogRepository) {
        this.followRepository = followRepository;
        this.dogRepository = dogRepository;
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

    @Transactional(readOnly = true)
    public FollowDto findByfollowerId(Integer followerid) {
        Optional<Follow> optionalFollow = followRepository.findById(followerid);
        if (optionalFollow.isPresent()) {
            Follow follow = optionalFollow.get();
            FollowDto followDto = new FollowDto(follow.getFollowerId(), follow.getFolloweeId());
            return followDto;
        }else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    public FollowDto findByfolloweeId(Integer followeeid) {
        Optional<Follow> optionalFollow = followRepository.findById(followeeid);
        if (optionalFollow.isPresent()) {
            Follow follow = optionalFollow.get();
            FollowDto followDto = new FollowDto(follow.getFollowerId(), follow.getFolloweeId());
            return followDto;
        }else{
            return null;
        }
    }

    @Transactional
    public void deleteFollowById(Integer id) {
        if(!followRepository.existsById(id)) {
            throw new EntityNotFoundException("Follow not found with ID: " + id);
        }
        dogRepository.deleteById(id);
    }
}
