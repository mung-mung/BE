package api.walking;


import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WalkingService {
    private final WalkingRepository walkingRepository;
    public WalkingService(WalkingRepository walkingRepository){
        this.walkingRepository = walkingRepository;
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

}
