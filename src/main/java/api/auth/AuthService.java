package api.auth;

import api.auth.dto.SignUpDto;
import api.user.admin.Admin;
import api.user.admin.AdminRepository;
import api.user.dto.UserAccountDto;
import api.user.owner.Owner;
import api.user.owner.repository.OwnerRepository;
import api.user.userAccount.UserAccount;
import api.user.userAccount.repository.UserAccountRepository;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.walker.Walker;
import api.user.walker.repository.WalkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final OwnerRepository ownerRepository;
    private final WalkerRepository walkerRepository;
    private final AdminRepository adminRepository;
    public AuthService(UserAccountRepository userAccountRepository, OwnerRepository ownerRepository, WalkerRepository walkerRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownerRepository = ownerRepository;
        this.walkerRepository = walkerRepository;
        this.adminRepository = adminRepository;
    }


    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isValidPw(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public UserAccountDto signUp(SignUpDto signUpDto) {
        String email = signUpDto.getEmail();
        if (userAccountRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("duplicated email");
        }
        String userName = signUpDto.getUserName();
        if(userAccountRepository.findByUserName(userName).isPresent()){
            throw new IllegalStateException("duplicated user name");
        }
        Role role = signUpDto.getRole();
        String pw = signUpDto.getPw();
        String avatarUrl = signUpDto.getAvatarUrl();
        String contact = signUpDto.getContact();
        Gender gender = signUpDto.getGender();
        LocalDate birthday = signUpDto.getBirthday();
        String hashedPw = passwordEncoder.encode(pw);
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPw(pw)) {
            throw new IllegalArgumentException("Invalid pw format");
        }
        if (role == Role.OWNER) {
            Owner owner = new Owner(email, userName, role, hashedPw, contact, gender, birthday);
            ownerRepository.save(owner);
        } else if (role == Role.WALKER) {
            Walker walker = new Walker(email, userName, role, hashedPw, contact, gender, birthday);
            walkerRepository.save(walker);
        } else if (role == Role.ADMIN) {
            Admin admin = new Admin(email, userName, role, hashedPw, contact, gender, birthday);
            adminRepository.save(admin);
        }
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findByEmail(email);
        UserAccount userAccount;
        if (optionalUserAccount.isPresent()) {
            userAccount = optionalUserAccount.get();
        } else {
            throw new RuntimeException("Signup failed with the email " + email);
        }
        UserAccountDto userAccountDto = new UserAccountDto(userAccount);
        return userAccountDto;
    }

}


