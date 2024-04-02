package api.dog;

import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }
    @Transactional(readOnly = true)
    public List<DogDto> findAllDogs() {
        return dogRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }
    @Transactional
    public DogDto createDog(DogDto dogDto) {
        Dog dog = new Dog(dogDto.getName(), dogDto.getBirthday(), dogDto.getBreed(), dogDto.getWeight(), dogDto.getSex());
        Dog savedDog = dogRepository.save(dog);
        return entityToDto(savedDog);
    }
    @Transactional(readOnly = true)
    public DogDto findDogById(Integer dogId) {
        Dog dog = dogRepository.findById(dogId).orElseThrow(() -> new EntityNotFoundException("Dog not found with ID: " + dogId));
        return entityToDto(dog);
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

    private DogDto entityToDto(Dog dog) {
        return new DogDto(dog.getId(), dog.getName(), dog.getBirthday(), dog.getBreed(), dog.getWeight(), dog.getSex());
    }
}
