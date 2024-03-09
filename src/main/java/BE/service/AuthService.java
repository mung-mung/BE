package BE.service;

import BE.domain.User;
import BE.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String signUp(User user){
        return "temp";
    }
    public String signIn(User user){
        return "temp";
    }
}
