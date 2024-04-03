package api.user.userAccount;

import api.common.util.http.HttpResponse;
import api.user.dto.UserAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user/account")
@Controller
public class UserAccountController {
    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findAllUsers() {
        return HttpResponse.successOk("Users fetched successfully", userAccountService.findAllUsers());
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> findUserById(@PathVariable Integer userId) {
        return userAccountService.findUserById(userId)
                .map(user -> HttpResponse.successOk("User found", user))
                .orElseGet(() -> HttpResponse.notFound("User not found", null));
    }

//    @PatchMapping("/{userId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateUserById(@PathVariable Integer userId, @RequestBody UserAccountDto userAccountDto) {
//    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> deleteUserById(@PathVariable Integer userId) {
        userAccountService.deleteUserById(userId);
        return HttpResponse.successOk("User deleted successfully", null);
    }

}
