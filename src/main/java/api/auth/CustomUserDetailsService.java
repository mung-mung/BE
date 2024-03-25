package api.auth;

import api.auth.dto.CustomUserDetails;
import api.user.userAccount.UserAccount;
import api.user.userAccount.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userData = userAccountRepository.findByEmail(email)
                .orElse(null);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
