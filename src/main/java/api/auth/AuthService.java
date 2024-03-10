package api.auth;

import api.user.User;
import api.user.UserRepository;

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


