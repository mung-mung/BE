package api.dog;

import api.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog, Long> { //JpaRepository 확장하기?

    List<Dog> findByOwner(Dog owner);
    List<Dog> findByWalker(Dog walker);

}
