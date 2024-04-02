package api.owning;

import api.common.util.http.HttpResponse;
import api.owning.dto.OwningDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/owning")
@Controller
public class OwningController {
    private final OwningService owningService;
    public OwningController(OwningService owningService){
        this.owningService = owningService;
    }
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findOwning(
                                               @RequestParam(value = "id", required = false) Integer id,
                                               @RequestParam(value = "ownerId", required = false) Integer ownerId,
                                               @RequestParam(value = "dogId", required = false) Integer dogId)
    }

}
