package api.auth;

import api.auth.dto.SignInDto;
import api.auth.dto.SignUpDto;
import api.user.User;
import api.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpDto signUp(SignUpDto signUpDto){
        String email = signUpDto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("duplicated email");
        }
        User.UserType userType = signUpDto.getUserType();
        String pw = signUpDto.getPw();
        String contact = signUpDto.getContact();
        User.Gender gender= signUpDto.getGender() ;
        LocalDateTime birthday = signUpDto.getBirthday();
        String hashedPw = passwordEncoder.encode(pw);
        signUpDto.setPw(hashedPw);
        User user = new User();
        user.setEmail(email);
        user.setUserType(userType);
        user.setPw(hashedPw);
        user.setContact(contact);
        user.setGender(gender);
        user.setBirthday(birthday);
        User savedUser = userRepository.save(user);
        return signUpDto;
    }

    public void signIn(SignInDto signInDto){
    }
}


