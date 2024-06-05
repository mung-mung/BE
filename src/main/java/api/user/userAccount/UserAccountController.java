package api.user.userAccount;

import api.common.util.http.HttpResponse;
import api.user.enums.Gender;
import api.user.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<Object> findAllUsers(@RequestParam(value = "id", required = false) Integer id,
                                               @RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "userName", required = false) String userName,
                                               @RequestParam(value = "role", required = false) Role role,
                                               @RequestParam(value = "contact", required = false) String contact,
                                               @RequestParam(value = "gender", required = false) Gender gender,
                                               @RequestParam(value = "birthday", required = false) LocalDate birthday) {
        return HttpResponse.successOk("All users found successfully", userAccountService.findUsersByAllCriteria(id, email, userName, role, contact, gender, birthday));
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
