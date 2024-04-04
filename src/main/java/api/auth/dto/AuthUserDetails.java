package api.auth.dto;

import api.user.userAccount.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AuthUserDetails implements UserDetails {
    //DB에서 유저 조회 후 리턴
    private final UserAccount userAccount;

    public AuthUserDetails(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userAccount.getRole().toString(); //계정의 Role 정보 확인
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userAccount.getPw();
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}

