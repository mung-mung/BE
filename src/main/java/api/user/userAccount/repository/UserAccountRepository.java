package api.user.userAccount.repository;

import api.user.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>, UserAccountRepositoryCustom {
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUserName(String userName);
}
