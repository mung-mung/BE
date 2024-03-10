package api.common.config;





import api.auth.AuthService;
import api.user.UserRepository;
import api.user.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApiConfig {
    private final DataSource dataSource;
    private final EntityManager em;
    public ApiConfig(DataSource dataSource, EntityManager em){
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public AuthService authService(){
        return new AuthService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepository(em);
    }

}

