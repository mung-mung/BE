package api.dog;

import api.common.util.http.HttpResponse;
import api.dog.dto.DogDto;
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


    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findAllDogs() {

    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> createDog(@RequestBody DogDto dogDto) {
        try {
            DogDto addedDog = dogService.createDog(dogDto);
            return HttpResponse.successCreated("Dog successfully created.", addedDog);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating dog: " + e.getMessage(), dogDto);
        }
    }


    @GetMapping("/{dogId}")
    @ResponseBody
    public ResponseEntity<Object> findDogById(@PathVariable Integer dogId) {
        try {
            DogDto foundDog = dogService.findDogByDogId(dogId);
            return HttpResponse.successOk("Dog successfully found.", foundDog);
        } catch (Exception e) {
            return HttpResponse.notFound("Dog not found: " + e.getMessage(), null);
        }
    }

    @PatchMapping("/{dogId}")
    @ResponseBody
    public ResponseEntity<Object> updateDogById(@PathVariable Integer dogId) {

    }

    @DeleteMapping("/{dogId}")
    @ResponseBody
    public ResponseEntity<Object> deleteDogById(@PathVariable Integer dogId) {

    }
}
