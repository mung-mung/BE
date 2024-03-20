package api.common.config;





import api.auth.AuthService;
import api.user.userAccount.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
public class ApiConfig {
    private final DataSource dataSource;
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;
    public ApiConfig(DataSource dataSource, EntityManager em, PasswordEncoder passwordEncoder){
        this.dataSource = dataSource;
        this.em = em;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserAccountService userService(UserAccountRepository userAccountRepository){
        return new UserAccountService(userAccountRepository);
    }

    @Bean
    public AuthService authService(UserAccountRepository userAccountRepository){
        return new AuthService(userAccountRepository, passwordEncoder);
    }

}

