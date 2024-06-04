package api.user.walker.repository;

import api.user.walker.Walker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkerRepository extends JpaRepository<Walker, Integer>, WalkerRepositoryCustom {
    Optional<Walker> findByEmail(String email);
}
