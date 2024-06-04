package api.owning.repository;


import api.owning.Owning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwningRepository extends JpaRepository<Owning, Integer>, OwningRepositoryCustom {
    List<Owning> findByOwnerId(Integer ownerId);
    List<Owning> findByDogId(Integer dogId);
    Optional<Owning> findByOwnerIdAndDogId(Integer ownerId, Integer dogId);
}


