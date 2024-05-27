package api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TestController {
    @GetMapping({"", "/"})
    public String testServerActive(){
        return "이 글이 보이면 서버는 정상임";
    }
}
