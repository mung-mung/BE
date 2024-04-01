package api.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

@RequestMapping("/api/admin")
@Controller
@ResponseBody
public class AdminController {
    //Admin page
    // 접근 권한 테스트 용 - 현재는 admin role 존재하지 않음
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminTest(){
        //get name
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        //get role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "admin test: " + name + " role: " + role;
    }
}
