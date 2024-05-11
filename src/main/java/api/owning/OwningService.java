package api.owning;


import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OwningService {
    private final OwningRepository owningRepository;
    public OwningService(OwningRepository owningRepository){
        this.owningRepository = owningRepository;
    }
    @Transactional(readOnly = true)
    public List<Owning> findAllOwnings(Integer id, Integer ownerId, Integer dogId) {
        if (id != null) {
            return owningRepository.findById(id)
                    .map(List::of)
                    .orElseGet(ArrayList::new);
        }

        List<Owning> results = new ArrayList<>();

        // ownerId와 dogId가 모두 null이 아닌 경우
        if (ownerId != null && dogId != null) {
            List<Owning> ownerResults = owningRepository.findByOwnerId(ownerId);
            List<Owning> dogResults = owningRepository.findByDogId(dogId);
            results = ownerResults.stream()
                    .filter(dogResults::contains)
                    .collect(Collectors.toList());
            return results;
        }

        // ownerId 또는 dogId 중 하나만 null인 경우
        if (ownerId != null) {
            results.addAll(owningRepository.findByOwnerId(ownerId));
        } else if (dogId != null) {
            results.addAll(owningRepository.findByDogId(dogId));
        }

        // 모든 인자가 null인 경우
        if (ownerId == null && dogId == null) {
            results.addAll(owningRepository.findAll());
        }

        return results;
    }

}
