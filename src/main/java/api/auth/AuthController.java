package api.auth;

import api.auth.dto.SignUpDto;
import api.common.util.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto signUpDto){
        try {
            SignUpDto signUpResult = authService.signUp(signUpDto);
            return HttpResponse.successCreated("User signup finished successfully", signUpResult);
        } catch (Exception e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        }
    }


    //로그인 리퀘스트는 '/login'으로 전송
    @GetMapping("/signIn")
    @ResponseBody
    public void signIn(){

    }

    @GetMapping("/signOut")
    @ResponseBody
    public void signOut(){}
}
