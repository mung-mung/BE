package api.walking;

import api.common.util.http.HttpResponse;
import api.walking.dto.WalkingDto;
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

    @PostMapping("/")
    public ResponseEntity<Object> createWalking(@RequestBody WalkingDto walkingDto) {
        try {
            Walking walking = walkingService.createWalking(walkingDto);
            return HttpResponse.successCreated("Walking created successfully", walking);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest("Invalid walker ID or dog ID", null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error creating walking", null);
        }
    }

    @DeleteMapping("/{walkingId}")
    public ResponseEntity<Object> deleteWalking(@PathVariable Integer walkingId) {
        try {
            walkingService.deleteWalkingById(walkingId);
            return HttpResponse.successOk("Walking deleted successfully", null);
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound("Walking not found", null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting walking", null);
        }
    }
}