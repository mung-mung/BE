package api.auth;

import api.auth.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@Controller
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/test")
    @ResponseBody
    public int test(){
        return 1;
    }

    @PostMapping("/signUp")
    @ResponseBody
    public SignUpDto signUp(@RequestBody SignUpDto signUpDto){
        return authService.signUp(signUpDto);
    }

    @PostMapping("/signIn")
    @ResponseBody
    public void signIn(){

    }

    @GetMapping("/signOut")
    @ResponseBody
    public void signOut(){}
}
