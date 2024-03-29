package api.walking;

import api.dog.Dog;
import api.user.walker.Walker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkingRepository extends JpaRepository<Walking, Integer> {
    Optional<Walking> findByWalkerAndDog(Walker walker, Dog dog);
}