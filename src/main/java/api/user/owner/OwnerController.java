package api.user.owner;

import api.common.util.http.HttpResponse;
import api.dog.Dog;
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
    }

    @GetMapping("/{ownerId}")
    @ResponseBody
    public ResponseEntity<Object> findOwnerById(@PathVariable Integer ownerId) {
    }

    @PatchMapping("/{ownerId}")
    @ResponseBody
    public ResponseEntity<Object> updateOwnerById(@PathVariable Integer ownerId) {
    }

}
