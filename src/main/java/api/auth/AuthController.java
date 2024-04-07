package api.auth;


import api.auth.dto.SignUpDto;
import api.common.util.http.HttpResponse;
import api.common.util.jwt.JwtGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@Controller
public class AuthController {
    private final AuthService authService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthService authService, JwtGenerator jwtGenerator){

        this.authService = authService;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/test")
    @ResponseBody
    public int test(){
        return 1;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto signUpDto){
        try {
            SignUpDto signUpResult = authService.signUp(signUpDto);
            return HttpResponse.successCreated("User signup finished successfully", signUpResult);
        } catch (Exception e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //Get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }


        if (refresh == null) {
            return new ResponseEntity<>("Refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtGenerator.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtGenerator.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtGenerator.getEmail(refresh);
        String role = jwtGenerator.getRole(refresh);

        //새로운 Access 토큰 발급
        String newAccess = jwtGenerator.createJwt("access", email, role, 60 * 10000L);

        response.setHeader("access", newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
