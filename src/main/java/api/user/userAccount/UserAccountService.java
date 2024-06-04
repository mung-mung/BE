package api.user.userAccount;


import api.user.dto.UserAccountDto;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.repository.UserAccountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    public UserAccountService(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<UserAccountDto> findUsersByAllCriteria(Integer id, String email, String userName, Role role, String contact, Gender gender, LocalDate birthday){
        return userAccountRepository.findUsersByAllCriteria(id, email, userName, role, contact, gender, birthday);
    }

    @Transactional(readOnly = true)
    public UserAccountDto findUserByEmail(String email) {
        Optional<UserAccount> optionalUserAccount =  userAccountRepository.findByEmail(email);
        if(optionalUserAccount.isPresent()){
            UserAccount userAccount = optionalUserAccount.get();
            UserAccountDto dto  = new UserAccountDto(userAccount);
            return dto;
        }else{
            return null;
        }
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
