package api.walking;

import api.dog.Dog;
import api.dog.DogRepository;
import api.user.walker.Walker;
import api.user.walker.WalkerRepository;
import api.walking.dto.WalkingDto;
import jakarta.transaction.Transactional;

import java.util.Optional;
@Transactional
public class WalkingService {
    private final WalkingRepository walkingRepository;
    private final WalkerRepository walkerRepository;
    private final DogRepository dogRepository;
    public WalkingService(WalkingRepository walkingRepository, WalkerRepository walkerRepository, DogRepository dogRepository){
        this.walkingRepository = walkingRepository;
        this.walkerRepository = walkerRepository;
        this.dogRepository = dogRepository;

    }
    public WalkingDto createWalking(WalkingDto walkingDto) {
        Optional<Walker> walker = walkerRepository.findById(walkingDto.getWalkerId());
        Optional<Dog> dog = dogRepository.findById(walkingDto.getDogId());

        if (walker.isPresent() && dog.isPresent()) {
            Walking walking = new Walking(walker.get(), dog.get());
            walkingRepository.save(walking);
            return walkingDto;
        } else {
            throw new RuntimeException("Walker or Dog not found");
        }
    }

    public WalkingDto deleteWalking(WalkingDto walkingDto) {
        Optional<Walker> walker = walkerRepository.findById(walkingDto.getWalkerId());
        Optional<Dog> dog = dogRepository.findById(walkingDto.getDogId());

        if (walker.isPresent() && dog.isPresent()) {
            Optional<Walking> walking = walkingRepository.findByWalkerAndDog(walker.get(), dog.get());
            walking.ifPresent(walkingRepository::delete);
            return walkingDto;
        } else {
            throw new RuntimeException("Walker or Dog not found");
        }
    }
}
