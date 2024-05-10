package api.dog;

import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
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
    public DogDto createDog(DogDto dogDto) {
        Dog dog = new Dog(dogDto.getName(), dogDto.getBirthday(), dogDto.getBreed(), dogDto.getWeight(), Sex.valueOf(dogDto.getSex().toUpperCase()));
        Dog savedDog = dogRepository.save(dog);
        return new DogDto(savedDog);
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
