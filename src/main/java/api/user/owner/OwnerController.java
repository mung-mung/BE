package api.user.owner;

import api.common.util.http.HttpResponse;
import api.user.owner.dto.OwnerDto;
import api.user.enums.Gender;
import api.user.owner.dto.OwningDogsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/user/owner")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> findAllOwners(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "contact", required = false) String contact,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "birthday", required = false) LocalDate birthday
    ) {
        List<OwnerDto> owners = ownerService.findOwnersByAllCriteria(id, email, userName, contact, gender, birthday);
        return HttpResponse.successOk("All owners found successfully", owners);
    }


    @GetMapping("dogs")
    @ResponseBody
    public ResponseEntity<Object> findAllOwningDogs() {
        try{
            List<OwningDogsDto> owningDogsDto = ownerService.findAllOwningDogs();
            return HttpResponse.successOk("All dogs found successfully", owningDogsDto);
        }catch(AccessDeniedException e){
            return HttpResponse.forbidden(e.getMessage(), null);
        }catch (Exception e){
            return HttpResponse.internalError(e.getMessage(), null);
        }
    }



//    @PatchMapping("/{ownerId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateOwnerById(@PathVariable Integer ownerId) {
//    }

}
