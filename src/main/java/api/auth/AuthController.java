package api.auth;

import api.auth.dto.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/api/auth/signUp")
    @ResponseBody
    public Jwt signUp(){
        Jwt jwt = new Jwt();
        jwt.setToken("test jwt");
        return jwt;
    }

    @PostMapping("/api/auth/signIn")
    @ResponseBody
    public Jwt signIn(){
        Jwt jwt = new Jwt();
        jwt.setToken("test jwt");
        return jwt;
    }

    @GetMapping("/api/auth/signOut")
    @ResponseBody
    public void signOut(){}
}
