package api.user.walker;

import api.common.util.http.HttpResponse;
import api.user.dto.WalkerDto;
import api.user.enums.Gender;
import api.user.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/user/walker")
@Controller
public class WalkerController {
    private final WalkerService walkerService;

    @Autowired
    public WalkerController(WalkerService walkerService) {
        this.walkerService = walkerService;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> findAllWalkers(@RequestParam(value = "id", required = false) Integer id,
                                                 @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "userName", required = false) String userName,
                                                 @RequestParam(value = "contact", required = false) String contact,
                                                 @RequestParam(value = "gender", required = false) Gender gender,
                                                 @RequestParam(value = "birthday", required = false) LocalDate birthday) {
        List<WalkerDto> walkers = walkerService.findWalkersByAllCriteria(id, email, userName, contact, gender, birthday);
        return HttpResponse.successOk("All walkers found successfully", walkers);
    }

//    @PatchMapping("/{walkerId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateWalkerById(@PathVariable Integer walkerId) {
//    }

}
