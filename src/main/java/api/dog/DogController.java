package api.dog;

import api.common.util.http.HttpResponse;
import api.dog.dto.DogDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/dog")
@Controller
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }


    @GetMapping({"/", ""})
    @ResponseBody
    public ResponseEntity<Object> findAllDogs() {
        try {
            return HttpResponse.successOk("All dogs fetched successfully.", dogService.findAllDogs());
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to fetch dogs: " + e.getMessage(), null);
        }
    }

    @PostMapping({"/", ""})
    @ResponseBody
    public ResponseEntity<Object> createDog(@RequestBody DogDto dogDto) {
        try {
            DogDto addedDog = dogService.createDog(dogDto);
            return HttpResponse.successCreated("Dog successfully created.", addedDog);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating dog: " + e.getMessage(), null);
        }
    }



    @GetMapping("/{dogId}")
    @ResponseBody
    public ResponseEntity<Object> findDogById(@PathVariable Integer dogId) {
        try {
            DogDto foundDog = dogService.findDogById(dogId);
            if (foundDog != null) {
                return HttpResponse.successOk("Dog successfully found.", foundDog);
            } else {
                return HttpResponse.notFound("Dog not found with ID: " + dogId, null);
            }
        } catch (Exception e) {
            return HttpResponse.internalError("Error finding dog: " + e.getMessage(), null);
        }
    }

//    @PatchMapping("/{dogId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateDogById(@PathVariable Integer dogId) {
//
//    }

    @DeleteMapping("/{dogId}")
    @ResponseBody
    public ResponseEntity<Object> deleteDogById(@PathVariable Integer dogId) {
        try {
            dogService.deleteDogById(dogId);
            return HttpResponse.successOk("Dog successfully deleted.", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting dog: " + e.getMessage(), null);
        }
    }
}
