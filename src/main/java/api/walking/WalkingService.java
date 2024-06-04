package api.walking;


import api.common.util.auth.loggedInUser.LoggedInUser;
import api.dog.Dog;
import api.dog.repository.DogRepository;
import api.owning.dto.OwningDto;
import api.walking.dto.WalkingDto;
import api.user.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.walker.Walker;
import api.user.walker.repository.WalkerRepository;
import api.walking.dto.CreateWalkingDto;
import api.walking.repository.WalkingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WalkingService {
    private final WalkingRepository walkingRepository;
    private final DogRepository dogRepository;
    private final WalkerRepository walkerRepository;
    public WalkingService(WalkingRepository walkingRepository, DogRepository dogRepository, WalkerRepository walkerRepository){
        this.walkingRepository = walkingRepository;
        this.dogRepository = dogRepository;
        this.walkerRepository = walkerRepository;
    }
    @Transactional(readOnly = true)
    public List<WalkingDto> findOwningsByAllCriteria(Integer id, Integer walkerId, Integer dogId){
        return walkingRepository.findWalkingsByAllCriteria(id, walkerId, dogId);
    }

    @Transactional
    public WalkingDto createWalking(CreateWalkingDto createWalkingDto) throws AccessDeniedException {
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();

        // 사용자 정보가 없거나 Role이 WALKER가 아니면 AccessDeniedException 발생
        if (loggedInUserAccountDto == null || !Role.WALKER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only walkers can create walking records.");
        }

        // UserAccountDto에서 Walker 객체 조회
        Optional<Walker> optionalWalker = walkerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalWalker.isEmpty()) {
            throw new AccessDeniedException("Walker not found for the logged in user.");
        }
        Walker walker = optionalWalker.get();

        // Dog 객체 조회
        Optional<Dog> optionalDog = dogRepository.findById(createWalkingDto.getDogId());
        if(optionalDog.isEmpty()){
            throw new IllegalArgumentException("Invalid dog ID");
        }
        Dog dog = optionalDog.get();

        Walking walking = new Walking(walker, dog);
        Walking savedWalking = walkingRepository.save(walking);
        WalkingDto walkingDto = new WalkingDto(savedWalking);
        return walkingDto;
    }

    @Transactional
    public void deleteWalkingById(Integer walkingId) throws AccessDeniedException {
        // 현재 로그인된 사용자 정보 가져오기
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();

        // 사용자 정보가 없거나 Role이 WALKER가 아니면 AccessDeniedException 발생
        if (loggedInUserAccountDto == null || !Role.WALKER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only walkers can delete walking records.");
        }

        // UserAccountDto에서 Walker 객체 조회
        Optional<Walker> optionalWalker = walkerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalWalker.isEmpty()) {
            throw new AccessDeniedException("Walker not found for the logged in user.");
        }
        Walker walker = optionalWalker.get();

        // Walking 객체 조회
        Optional<Walking> optionalWalking = walkingRepository.findById(walkingId);
        if (optionalWalking.isEmpty()) {
            throw new EntityNotFoundException("Walking not found with ID: " + walkingId);
        }
        Walking walking = optionalWalking.get();

        // Walker와 Walking 관계 확인
        if (!walking.getWalker().equals(walker)) {
            throw new AccessDeniedException("The logged-in user is not the walker of this walking record.");
        }

        // Walking 삭제
        walkingRepository.deleteById(walkingId);
    }

}
