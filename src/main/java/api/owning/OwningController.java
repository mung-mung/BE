package api.owning;

import api.common.util.http.HttpResponse;
import api.owning.dto.OwningDto;
import api.owning.dto.OwningDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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
        List<OwningDto> owningDtos = owningService.findOwningsByAllCriteria(id, ownerId, dogId);
        if (owningDtos.isEmpty()) {
            return HttpResponse.notFound("No ownings found matching criteria", null);
        } else {
            return HttpResponse.successOk("Ownings found successfully", owningDtos);
        }
    }

    @DeleteMapping("/{owningId}")
    public ResponseEntity<Object> deleteOwning(@PathVariable Integer owningId) {
        try {
            owningService.deleteOwningById(owningId);
            return HttpResponse.successOk("Owning deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting owning: " + e.getMessage(), null);
        }
    }
}
