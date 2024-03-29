package api.owning;


import api.dog.Dog;
import api.user.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwningRepository extends JpaRepository<Owning, Integer> {
    Optional<Owning> findByOwnerAndDog(Owner owner, Dog dog);
}


