package api.common.util.auth.loggedInUser;

import api.user.dto.UserAccountDto;
import api.user.userAccount.UserAccount;
import api.user.userAccount.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import api.auth.dto.AuthUserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoggedInUser {

    private static UserAccountRepository userAccountRepository;

    @Autowired
    public LoggedInUser(UserAccountRepository userAccountRepository) {
        LoggedInUser.userAccountRepository = userAccountRepository;
    }

    private static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthUserDetails) {
                AuthUserDetails userDetails = (AuthUserDetails) principal;
                return userDetails.getUsername();
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails.getUsername();
            } else if (principal instanceof String) {
                return (String) principal;
            }
        }
        return null;
    }

    public static UserAccountDto getCurrentUserDto() {
        String email = getCurrentUserEmail();
        if (email != null) {
            UserAccount userAccount = userAccountRepository.findByEmail(email).orElse(null);
            if (userAccount != null) {
                return new UserAccountDto(userAccount);
            }
        }
        return null;
    }
}
