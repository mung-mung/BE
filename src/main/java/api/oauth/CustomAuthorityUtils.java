package api.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthorityUtils {
    @Value("${mail.address.admin}")
    private String adminMailAddress;

    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_OWNER", "ROLE_WALKER");
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_OWNER", "ROLE_WALKER");

    public List<GrantedAuthority> createAuthorities(String email) {
        if (email.equals(adminMailAddress)){
            return ADMIN_ROLES;
        }
        return USER_ROLES;
    }
}
