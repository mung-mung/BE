package api.user.walker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalkerRepository extends JpaRepository<Walker, Integer> {
    Optional<Walker> findByEmail(String email);
    Optional<Walker> findByUserName(String userName);
}
