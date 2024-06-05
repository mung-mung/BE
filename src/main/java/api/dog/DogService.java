package api.dog;

import api.common.util.auth.loggedInUser.LoggedInUser;
import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import api.dog.repository.DogRepository;
import api.owning.Owning;
import api.owning.repository.OwningRepository;
import api.owning.dto.OwningDto;
import api.user.userAccount.dto.UserAccountDto;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.owner.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.*;

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
    public List<DogDto> findDogsByAllCriteria(Integer id, String name, LocalDate birthday, String breed, Float weight, Sex sex) {
        return dogRepository.findDogsByAllCriteria(id, name, birthday, breed, weight, sex);
    }
    @Transactional
    public Map<String, Object> createDog(DogDto dogDto) throws AccessDeniedException {
        Dog dog = new Dog(dogDto.getName(), dogDto.getBirthday(), dogDto.getBreed(), dogDto.getWeight(), dogDto.getSex());
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
    public void deleteDogById(Integer dogId) throws AccessDeniedException {
        // 현재 로그인된 사용자 정보 가져오기
        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();

        // 사용자 정보가 없거나 Role이 OWNER가 아니면 AccessDeniedException 발생
        if (loggedInUserAccountDto == null || !Role.OWNER.equals(loggedInUserAccountDto.getRole())) {
            throw new AccessDeniedException("Only owners can delete dogs.");
        }

        // Dog 존재 여부 확인
        if (!dogRepository.existsById(dogId)) {
            throw new EntityNotFoundException("Dog not found with ID: " + dogId);
        }

        // UserAccountDto에서 Owner 객체 조회
        Optional<Owner> optionalOwner = ownerRepository.findByEmail(loggedInUserAccountDto.getEmail());
        if (optionalOwner.isEmpty()) {
            throw new AccessDeniedException("Owner not found for the logged in user.");
        }
        Owner owner = optionalOwner.get();

        // Dog와 Owning 관계 확인
        Optional<Owning> owningOptional = owningRepository.findByOwnerIdAndDogId(owner.getId(), dogId);
        if (owningOptional.isEmpty()) {
            throw new AccessDeniedException("The logged-in user is not the owner of this dog.");
        }

        // Dog 삭제
        dogRepository.deleteById(dogId);
    }
}
