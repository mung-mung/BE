package api.walking;

import api.common.util.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/walking")
@Controller
public class WalkingController {
    private final WalkingService walkingService;
    public WalkingController(WalkingService walkingService){
        this.walkingService = walkingService;
    }
    @GetMapping("/")
    public ResponseEntity<Object> findWalkings(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "walkerId", required = false) Integer walkerId,
            @RequestParam(value = "dogId", required = false) Integer dogId) {
        List<Walking> walkings = walkingService.findWalkings(id, walkerId, dogId);
        if (walkings.isEmpty()) {
            return HttpResponse.notFound("No walkings found matching criteria", null);
        } else {
            return HttpResponse.successOk("Walkings found", walkings);
        }
    }


}
