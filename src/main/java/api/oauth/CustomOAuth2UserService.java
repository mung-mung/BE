package api.oauth;

import api.user.userAccount.UserAccount;
import api.user.userAccount.UserAccountRepository;
import api.oauth.OAuthAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserAccountRepository userAccountRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public CustomOAuth2UserService(UserAccountRepository userAccountRepository, CustomAuthorityUtils customAuthorityUtils) {
        this.userAccountRepository = userAccountRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //OAuth2 정보 가져옴
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Map<String, Object> originAttributes = oAuth2User.getAttributes();

        //OAuth 서비스 ID
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);

        String email = attributes.getEmail();

        List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities(email);

        return new OAuth2CustomUser(registrationId, originAttributes, authorities, email);
    }

//    private UserAccount saveOrUpdate(OAuthAttributes authAttributes) {
//        UserAccount userAccount = userAccountRepository.findByEmail(authAttributes.getEmail())
//                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getProfileImageUrl()))
//                .orElse(authAttributes.toEntity());
//    }

}
