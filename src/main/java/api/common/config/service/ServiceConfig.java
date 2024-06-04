package api.common.config.service;

import api.auth.AuthService;
import api.dog.repository.DogRepository;
import api.dog.DogService;
import api.owning.OwningRepository;
import api.owning.OwningService;
import api.user.admin.AdminRepository;
import api.user.owner.repository.OwnerRepository;
import api.user.owner.OwnerService;
import api.user.userAccount.repository.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import api.user.walker.repository.WalkerRepository;
import api.user.walker.WalkerService;
import api.walking.WalkingRepository;
import api.walking.WalkingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    private final PasswordEncoder passwordEncoder;

    public ServiceConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserAccountService userService(UserAccountRepository userAccountRepository) {
        return new UserAccountService(userAccountRepository);
    }

    @Bean
    public AuthService authService(UserAccountRepository userAccountRepository, OwnerRepository ownerRepository, WalkerRepository walkerRepository, AdminRepository adminRepository) {
        return new AuthService(userAccountRepository, ownerRepository, walkerRepository, adminRepository, passwordEncoder);
    }

    @Bean
    public DogService dogService(DogRepository dogRepository, OwnerRepository ownerRepository, OwningRepository owningRepository) {
        return new DogService(dogRepository, ownerRepository, owningRepository);
    }

    @Bean
    public OwnerService ownerService(OwnerRepository ownerRepository) {
        return new OwnerService(ownerRepository);
    }

    @Bean
    public WalkerService walkerService(WalkerRepository walkerRepository) {
        return new WalkerService(walkerRepository);
    }

    @Bean
    public OwningService owningService(OwningRepository owningRepository, OwnerRepository ownerRepository) {
        return new OwningService(owningRepository, ownerRepository);
    }

    @Bean
    public WalkingService walkingService(WalkingRepository walkingRepository, DogRepository dogRepository, WalkerRepository walkerRepository) {
        return new WalkingService(walkingRepository, dogRepository, walkerRepository);
    }
}
