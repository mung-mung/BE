package api.common.util.jwt;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class JwtTestUtil {
    public static RequestPostProcessor mockUser(String username, String... roles) {
        return SecurityMockMvcRequestPostProcessors.user(username).roles(roles);
    }
}