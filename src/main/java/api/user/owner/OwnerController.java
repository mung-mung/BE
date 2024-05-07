package api.user.owner;

import api.common.util.http.HttpResponse;
import api.dog.Dog;
import api.user.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/owner")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findAllOwners() {
        List<OwnerDto> owners = ownerService.findAllOwners();
        return HttpResponse.successOk("Owners retrieved successfully", owners);
    }

    @GetMapping("/{ownerId}")
    @ResponseBody
    public ResponseEntity<Object> findOwnerById(@PathVariable Integer ownerId) {
        OwnerDto ownerDto = ownerService.findOwnerById(ownerId);
        if(ownerDto == null) {
            return HttpResponse.notFound("Error: Owner not found", null);
        }else{
            return HttpResponse.successOk("Owner found successfully", ownerDto);
        }
    }

//    @PatchMapping("/{ownerId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateOwnerById(@PathVariable Integer ownerId) {
//    }

}
