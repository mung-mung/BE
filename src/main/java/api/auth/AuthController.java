package api.auth;


import api.auth.dto.SignUpDto;
import api.auth.refresh.RefreshEntity;
import api.auth.refresh.RefreshRepository;
import api.common.util.http.HttpResponse;
import api.common.util.auth.jwt.JwtGenerator;
import api.user.userAccount.dto.UserAccountDto;
import api.user.userAccount.UserAccountService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/api/auth")
@Controller
public class AuthController {
    private final AuthService authService;
    private final UserAccountService userAccountService;
    private final JwtGenerator jwtGenerator;
    private final RefreshRepository refreshRepository;

    @Autowired
    public AuthController(AuthService authService, UserAccountService userAccountService, JwtGenerator jwtGenerator, RefreshRepository refreshRepository){
        this.authService = authService;
        this.userAccountService = userAccountService;
        this.jwtGenerator = jwtGenerator;
        this.refreshRepository = refreshRepository;
    }

    @GetMapping("/test")
    @ResponseBody
    public int test(){
        return 1;
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto signUpDto){
        try {
            UserAccountDto userAccountDto = authService.signUp(signUpDto);
            if(userAccountDto == null){
                return HttpResponse.internalError("Signup failed", null);
            }
            return HttpResponse.successCreated("User signup finished successfully", userAccountDto);
        } catch (Exception e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        }
    }

    @GetMapping("/decode-jwt")
    @ResponseBody
    public ResponseEntity<Object> decodeJwt(@RequestHeader("Authorization") String authorizationHeader){
        try{
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return HttpResponse.badRequest("Invalid JWT token format.", null);
            }
            String jwtToken = authorizationHeader.substring(7).trim();

            String email = jwtGenerator.getEmail(jwtToken);
            UserAccountDto userAccountDto = userAccountService.findUserByEmail(email);
            if(userAccountDto == null){
                return HttpResponse.notFound("Error: User not found", null);
            }else{
                return HttpResponse.successOk("Decoding Jwt token finished successfully", userAccountDto);
            }
        }catch(Exception e){
            return HttpResponse.badRequest(e.getMessage(), null);
        }
    }



    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //Get refresh token
        String refresh = null;
        try {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }catch(Exception e){
            return HttpResponse.internalError(e.getMessage(), null);
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

        //Refresh 토큰이 DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtGenerator.getEmail(refresh);
        String role = jwtGenerator.getRole(refresh);

        //새로운 Access, Refresh 토큰 발급
        String newAccess = jwtGenerator.createJwt("access", email, role, 60 * 10000L);
        String newRefresh = jwtGenerator.createJwt("refresh", email, role, 86400000L);

        //기존 Refresh 토큰을 DB에서 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, 86400000L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("Refresh", newRefresh));

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", newAccess);
        tokenMap.put("refreshToken", newRefresh);
        return HttpResponse.successOk("Reissue finished successfully", tokenMap);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String email, String refresh, Long expiredplusMillisecond) {
        LocalDateTime expirationDateTime = LocalDateTime.ofInstant(Instant.now().plusMillis(expiredplusMillisecond), ZoneId.systemDefault());
        RefreshEntity refreshEntity = new RefreshEntity(email, refresh, expirationDateTime);
        refreshRepository.save(refreshEntity);
    }

}
