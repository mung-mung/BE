package api.walking;


import api.dog.Dog;
import api.user.walker.Walker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalkingRepository extends JpaRepository<Walking, Integer> {
    List<Walking> findByWalkerId(Integer walkerId);
    List<Walking> findByDogId(Integer dogId);
    Optional<Walking> findByWalkerIdAndDogId(Integer walkerId, Integer dogId);
}


