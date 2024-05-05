package api.user.userAccount;


import api.user.dto.UserAccountDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    public UserAccountService(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }
    @Transactional(readOnly = true)
    public List<UserAccount> findAllUsers() {
        return userAccountRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<UserAccount> findUserById(Integer userId) {
        return userAccountRepository.findById(userId);
    }
    @Transactional(readOnly = true)
    public Optional<UserAccount> findUserByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    //    @Transactional
    //    public Optional<UserAccount> updateUserById(Integer userId, UserAccountDto userAccountDto) {
    //
    //    }
    @Transactional
    public void deleteUserById(Integer userId) {
        userAccountRepository.deleteById(userId);
    }
}
