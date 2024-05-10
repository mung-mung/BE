package api.dog;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import api.owning.Owning;
import api.owning.OwningRepository;
import api.owning.dto.OwningDto;
import api.user.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.OwnerRepository;
import api.user.userAccount.UserAccount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DogService {
    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;
    private final OwningRepository owningRepository;

    public DogService(DogRepository dogRepository, OwnerRepository ownerRepository, OwningRepository owningRepository) {
        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
        this.owningRepository = owningRepository;
    }
    @Transactional(readOnly = true)
    public List<DogDto> findAllDogs() {
        List<Dog> dogs = dogRepository.findAll();
        List<DogDto> dogDtos = new ArrayList<>();
        for(Dog dog : dogs){
            dogDtos.add(new DogDto(dog));
        }
        return dogDtos;
    }
    @Transactional
    public Map<String, Object> createDog(DogDto dogDto) throws AccessDeniedException {
        Dog dog = new Dog(dogDto.getName(), dogDto.getBirthday(), dogDto.getBreed(), dogDto.getWeight(), Sex.valueOf(dogDto.getSex().toUpperCase()));
        Dog savedDog = dogRepository.save(dog);
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can create dogs.");
        }
        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }
        Owner owner = optionalOwner.get();
        Owning owning  = new Owning(owner, dog);
        Owning savedOwining = owningRepository.save(owning);
        Map<String, Object> res = new HashMap<>();
        res.put("dogDto", new DogDto(savedDog));
        res.put("owningDto", new OwningDto(savedOwining));
        return res;
    }

    @Transactional(readOnly = true)
    public DogDto findDogById(Integer dogId) {
        Dog dog = dogRepository.findById(dogId).orElseThrow(() -> new EntityNotFoundException("Dog not found with ID: " + dogId));
        return new DogDto(dog);
    }
//    @Transactional
//    public DogDto updateDogById(Integer dogId, DogDto dogDto) {
//
//    }
    @Transactional
    public void deleteDogById(Integer dogId) {
        if (!dogRepository.existsById(dogId)) {
            throw new EntityNotFoundException("Dog not found with ID: " + dogId);
        }
        dogRepository.deleteById(dogId);
    }

}
