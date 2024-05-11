package api.owning;


import api.owning.Owning;
import api.owning.dto.OwningDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OwningService {
    private final OwningRepository owningRepository;
    public OwningService(OwningRepository owningRepository){
        this.owningRepository = owningRepository;
    }
    @Transactional(readOnly = true)
    public List<OwningDto> findOwnings(Integer id, Integer ownerId, Integer dogId){
        List<Owning> ownings = new ArrayList<>();
        if (id != null) {
            Optional<Owning> owning = owningRepository.findById(id);
            ownings = owning.map(List::of).orElse(List.of());
        } else if (ownerId != null && dogId != null) {
            Optional<Owning> optionalOwning = owningRepository.findByOwnerIdAndDogId(ownerId, dogId);
            Owning owning = optionalOwning.get();
            ownings.add(owning);
        } else if (ownerId != null) {
            ownings = owningRepository.findByOwnerId(ownerId);
        } else if (dogId != null) {
            ownings = owningRepository.findByDogId(dogId);
        } else {
            ownings = owningRepository.findAll();
        }
        return ownings.stream().map(OwningDto::new).collect(Collectors.toList());
    }

}
