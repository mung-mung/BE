package api.owning;


import api.dog.Dog;
import api.user.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwningRepository extends JpaRepository<Owning, Integer> {
    List<Owning> findByOwnerId(Integer ownerId);
    List<Owning> findByDogId(Integer dogId);

}


