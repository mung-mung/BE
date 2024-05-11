package api.owning;

import api.common.util.http.HttpResponse;
import api.owning.dto.OwningDto;
import api.walking.dto.WalkingDto;
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
    @GetMapping({"/", ""})
    public ResponseEntity<Object> findOwnings(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "owner_id", required = false) Integer ownerId,
            @RequestParam(value = "dogId", required = false) Integer dogId) {
        List<OwningDto> owningDtos = owningService.findOwnings(id, ownerId, dogId);
        if (owningDtos.isEmpty()) {
            return HttpResponse.notFound("No ownings found matching criteria", null);
        } else {
            return HttpResponse.successOk("Ownings found successfully", owningDtos);
        }
    }


}
