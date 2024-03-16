package api.auth;

import api.auth.dto.SignInDto;
import api.auth.dto.SignUpDto;
import api.user.User;
import api.user.repository.JpaUserRepository;
import api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if(!isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email format");
        }
        if(!isValidPw(pw)){
            throw new IllegalArgumentException("Invalid pw format");
        }
        signUpDto.setPw(hashedPw);
        User user = new User(email, userType, hashedPw, contact, gender, birthday);
        userRepository.save(user);
        return signUpDto;
    }

    public void signIn(SignInDto signInDto){
    }
}


