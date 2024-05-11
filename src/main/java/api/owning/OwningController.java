package api.owning;

import api.common.util.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/owning")
@Controller
public class OwningController {
    private final OwningService owningService;
    public OwningController(OwningService owningService){
        this.owningService = owningService;
    }
    @GetMapping("/")
    public ResponseEntity<Object> findOwnings(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "ownerId", required = false) Integer ownerId,
            @RequestParam(value = "dogId", required = false) Integer dogId) {
        List<Owning> ownings = owningService.findOwnings(id, ownerId, dogId);
        if (ownings.isEmpty()) {
            return HttpResponse.notFound("No ownings found matching criteria", null);
        } else {
            return HttpResponse.successOk("Ownings found", ownings);
        }
    }


}
