package api.walking;

import api.common.util.http.HttpResponse;
import api.walking.dto.WalkingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/walking")
@Controller
public class WalkingController {
    private final WalkingService walkingService;
    public WalkingController(WalkingService walkingService){
        this.walkingService = walkingService;
    }
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findWalking(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "walkerId", required = false) Integer walkerId,
            @RequestParam(value = "dogId", required = false) Integer dogId){

    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> createWalking(WalkingDto walkingDto){

    }

    @DeleteMapping
    @RequestBody
    public ResponseEntity<Object> deleteWalking(@PathVariable Integer walkingId){

    }

}
