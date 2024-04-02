package api.dog;

import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Transactional
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository){
        this.dogRepository = dogRepository;
    }
    public DogDto createDog(DogDto dogDto) {
        Dog dog = new Dog(dogDto.getName(), dogDto.getBirthday(), dogDto.getBreed(), dogDto.getWeight(), dogDto.getSex());
        dog = dogRepository.save(dog);
        return new DogDto(dog.getId(), dog.getName(), dog.getBirthday(), dog.getBreed(), dog.getWeight(), dog.getSex());
    }

    public DogDto deleteDog(DogDto dogDto) {
        return dogRepository.findById(dogDto.getId())
                .map(dog -> {
                    dogRepository.delete(dog);
                    return dogDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("Dog not found with id: " + dogDto.getId()));
    }

}
