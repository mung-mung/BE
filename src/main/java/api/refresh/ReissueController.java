package api.refresh;

import api.common.util.jwt.JwtGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class ReissueController {
    private final JwtGenerator jwtGenerator;

    public ReissueController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/api/auth/reissue")
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
        String newAccess = jwtGenerator.createJwt("access", email, role, 60*10000L);

        response.setHeader("access", newAccess);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
