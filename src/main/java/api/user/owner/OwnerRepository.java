package api.user.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Optional<Owner> findByEmail(String email);
    Optional<Owner> findByUserName(String userName);
}
