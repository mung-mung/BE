package api.walking;


import api.common.util.auth.loggedInUser.LoggedInUser;
import api.dog.Dog;
import api.dog.DogRepository;
import api.user.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.walker.Walker;
import api.user.walker.WalkerRepository;
import api.walking.dto.CreateWalkingDto;
import api.walking.dto.WalkingDto;
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
    public List<Walking> findWalkings(Integer id, Integer walkerId, Integer dogId) {
        if (id != null) {
            return walkingRepository.findById(id)
                    .map(List::of)
                    .orElseGet(ArrayList::new);
        }

        List<Walking> results = new ArrayList<>();

        // walkerId와 dogId가 모두 null이 아닌 경우
        if (walkerId != null && dogId != null) {
            List<Walking> walkerResults = walkingRepository.findByWalkerId(walkerId);
            List<Walking> dogResults = walkingRepository.findByDogId(dogId);
            results = walkerResults.stream()
                    .filter(dogResults::contains)
                    .collect(Collectors.toList());
            return results;
        }

        // walkerId 또는 dogId 중 하나만 null인 경우
        if (walkerId != null) {
            results.addAll(walkingRepository.findByWalkerId(walkerId));
        } else if (dogId != null) {
            results.addAll(walkingRepository.findByDogId(dogId));
        }

        // 모든 인자가 null인 경우
        if (walkerId == null && dogId == null) {
            results.addAll(walkingRepository.findAll());
        }

        return results;
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
    public void deleteWalkingById(Integer walkingId) {
        walkingRepository.deleteById(walkingId);
    }

}
