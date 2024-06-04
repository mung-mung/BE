package api.common.config;





import api.auth.AuthService;
import api.dog.DogRepository;
import api.dog.DogService;
import api.owning.OwningRepository;
import api.owning.OwningService;
import api.user.admin.AdminRepository;
import api.user.owner.OwnerRepository;
import api.user.owner.OwnerService;
import api.user.userAccount.repository.UserAccountRepository;
import api.user.userAccount.UserAccountService;
import api.user.walker.WalkerRepository;
import api.user.walker.WalkerService;
import api.walking.WalkingRepository;
import api.walking.WalkingService;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}

