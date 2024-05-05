package api.user.userAccount;


import api.user.dto.UserAccountDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    public UserAccountService(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }
    @Transactional(readOnly = true)
    public List<UserAccountDto> findAllUsers() {
        List<UserAccount> allUsers =  userAccountRepository.findAll();
        List<UserAccountDto> userAccountDtos = new ArrayList<>();
        for(UserAccount userAccount : allUsers){
            UserAccountDto dto = new UserAccountDto(userAccount);
            userAccountDtos.add(dto);
        }
        return userAccountDtos;
    }
    @Transactional(readOnly = true)
    public UserAccountDto findUserById(Integer userId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userId);
        if(optionalUserAccount.isPresent()){
            UserAccountDto dto  = new UserAccountDto(optionalUserAccount.get());
            return dto;
        }else{
            return null;
        }
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
