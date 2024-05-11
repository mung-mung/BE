package api.walking;

import api.common.util.http.HttpResponse;
import api.walking.dto.CreateWalkingDto;
import api.walking.dto.WalkingDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequestMapping("/api/walking")
@Controller
public class WalkingController {
    private final WalkingService walkingService;
    public WalkingController(WalkingService walkingService){
        this.walkingService = walkingService;
    }
    @GetMapping({"/", ""})
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

    @PostMapping({"/", ""})
    public ResponseEntity<Object> createWalking(@RequestBody CreateWalkingDto createWalkingDto) {
        try {
            WalkingDto walkingDto = walkingService.createWalking(createWalkingDto);
            return HttpResponse.successCreated("Walking created successfully", walkingDto);
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error creating walking: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/{walkingId}")
    public ResponseEntity<Object> deleteWalking(@PathVariable Integer walkingId) {
        try {
            walkingService.deleteWalkingById(walkingId);
            return HttpResponse.successOk("Walking deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting walking: " + e.getMessage(), null);
        }
    }
}
