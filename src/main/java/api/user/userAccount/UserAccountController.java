package api.user.userAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
@Controller
public class UserAccountController {
    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

}
