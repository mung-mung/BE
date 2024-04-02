package api.owning;

import api.common.util.http.HttpResponse;
import api.owning.dto.OwningDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/owning")
@Controller
public class OwningController {
    private final OwningService owningService;
    public OwningController(OwningService owningService){
        this.owningService = owningService;
    }
    @PostMapping("/createOwning")
    @ResponseBody
    public ResponseEntity<Object> createOwning(@RequestBody OwningDto owningDto){
        try {
            OwningDto createedOwning = owningService.createOwning(owningDto);
            return HttpResponse.successCreated("Owning successfully createed.", createedOwning);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error createing owning: " + e.getMessage(), null);
        }
    }
    @PostMapping("/deleteOwning")
    @ResponseBody
    public ResponseEntity<Object> deleteOwning(@RequestBody OwningDto owningDto) {
        try {
            OwningDto deletedOwning = owningService.deleteOwning(owningDto);
            return HttpResponse.successOk("Owning successfully deleted.", deletedOwning);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error deleting owning: " + e.getMessage(), null);
        }
    }
}
