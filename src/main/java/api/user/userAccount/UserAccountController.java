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
    @GetMapping({"/", ""})
    @ResponseBody
    public ResponseEntity<Object> findAllUsers() {
        return HttpResponse.successOk("All users found successfully", userAccountService.findAllUsers());
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<Object> findUserById(@PathVariable Integer userId) {
        UserAccountDto userAccountDto = userAccountService.findUserById(userId);
        if(userAccountDto == null){
            return HttpResponse.notFound("Error: User not found", null);
        }else{
            return HttpResponse.successOk("User found successfully", userAccountDto);
        }
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
