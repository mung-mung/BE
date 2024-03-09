package BE.controller;

import BE.model.auth.Token;
import BE.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Token signUp(){
        Token token = new Token();
        token.setToken("test token");
        return token;
    }
    @PostMapping("/api/auth/signIn")
    @ResponseBody
    public Token signIn(){
        Token token = new Token();
        token.setToken("test token");
        return token;
    }
}
