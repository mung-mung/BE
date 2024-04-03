package api.common.config;





import api.auth.AuthService;
import api.dog.DogRepository;
import api.dog.DogService;
import api.owning.OwningRepository;
import api.owning.OwningService;
import api.user.admin.AdminRepository;
import api.user.owner.OwnerRepository;
import api.user.owner.OwnerService;
import api.user.userAccount.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import api.user.walker.WalkerRepository;
import api.user.walker.WalkerService;
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
    public AuthService authService(UserAccountRepository userAccountRepository, OwnerRepository ownerRepository, WalkerRepository walkerRepository, AdminRepository adminRepository){
        return new AuthService(userAccountRepository, ownerRepository, walkerRepository, adminRepository, passwordEncoder);
    }

    @Bean
    public OwningService owningService(OwningRepository owningRepository){
        return new OwningService(owningRepository);
    }

    @Bean
    public WalkingService walkingService(WalkingRepository walkingRepository){
        return new WalkingService(walkingRepository);
    }

    @Bean
    public DogService dogService(DogRepository dogRepository){
        return new DogService(dogRepository);
    }

    @Bean
    public OwnerService ownerService(OwnerRepository ownerRepository){
        return new OwnerService(ownerRepository);
    }

    @Bean
    public WalkerService walkerService(WalkerRepository walkerRepository){
        return new WalkerService(walkerRepository);
    }

}

