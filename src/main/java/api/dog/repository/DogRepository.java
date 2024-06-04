package api.dog.repository;

import api.dog.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Integer>, DogRepositoryCustom {
}
