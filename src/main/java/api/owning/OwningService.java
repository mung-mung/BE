package api.owning;

import api.common.util.http.HttpResponse;
import api.dog.Dog;
import api.dog.DogRepository;
import api.owning.dto.OwningDto;
import api.user.owner.Owner;
import api.user.owner.OwnerRepository;
import jakarta.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
@Transactional
public class OwningService {
    private final OwningRepository owningRepository;
    private final OwnerRepository ownerRepository;
    private final DogRepository dogRepository;
    public OwningService(OwningRepository owningRepository, OwnerRepository ownerRepository, DogRepository dogRepository){
        this.owningRepository = owningRepository;
        this.ownerRepository = ownerRepository;
        this.dogRepository = dogRepository;

    }
    public OwningDto createOwning(OwningDto owningDto) {
        Optional<Owner> owner = ownerRepository.findById(owningDto.getOwnerId());
        Optional<Dog> dog = dogRepository.findById(owningDto.getDogId());

        if (owner.isPresent() && dog.isPresent()) {
            Owning owning = new Owning(owner.get(), dog.get());
            owningRepository.save(owning);
            return owningDto;
        } else {
            throw new RuntimeException("Owner or Dog not found");
        }
    }

    public OwningDto deleteOwning(OwningDto owningDto) {
        Optional<Owner> owner = ownerRepository.findById(owningDto.getOwnerId());
        Optional<Dog> dog = dogRepository.findById(owningDto.getDogId());

        if (owner.isPresent() && dog.isPresent()) {
            Optional<Owning> owning = owningRepository.findByOwnerAndDog(owner.get(), dog.get());
            owning.ifPresent(owningRepository::delete);
            return owningDto;
        } else {
            throw new RuntimeException("Owner or Dog not found");
        }
    }
}
