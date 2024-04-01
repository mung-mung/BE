package api.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/admin")
@Controller
@ResponseBody
public class AdminController {
    //Admin page
    // 접근 권한 테스트 용 - 현재는 admin role 존재하지 않음
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('OWNER')") //OWNER로만 접근 가능, 권한 없을 시 로그인 페이지로 리다이렉트
    public String adminTest(){
        return "admin test authorized";
    }
}
