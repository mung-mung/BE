package api.user.owner.repository;

import api.user.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer>, OwnerRepositoryCustom {
    Optional<Owner> findByEmail(String email);
}
