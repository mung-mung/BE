package api.user.userAccount;


public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    public UserAccountService(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }
}
