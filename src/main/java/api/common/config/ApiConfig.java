package api.common.config;





import api.auth.AuthService;
import api.dog.DogRepository;
import api.owning.OwningRepository;
import api.owning.OwningService;
import api.user.owner.OwnerRepository;
import api.user.userAccount.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import api.user.walker.WalkerRepository;
import api.walking.WalkingRepository;
import api.walking.WalkingService;
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
    public AuthService authService(UserAccountRepository userAccountRepository, OwnerRepository ownerRepository, WalkerRepository walkerRepository){
        return new AuthService(userAccountRepository, ownerRepository, walkerRepository, passwordEncoder);
    }

    @Bean
    public OwningService owningService(OwningRepository owningRepository, OwnerRepository ownerRepository, DogRepository dogRepository){
        return new OwningService(owningRepository, ownerRepository, dogRepository);
    }

    @Bean
    public WalkingService walkingService(WalkingRepository walkingRepository, WalkerRepository walkerRepository, DogRepository dogRepository){
        return new WalkingService(walkingRepository, walkerRepository, dogRepository);
    }


}

