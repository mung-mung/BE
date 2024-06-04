package api.dog;

import api.common.util.http.HttpResponse;
import api.dog.dto.DogDto;
import api.dog.enums.Sex;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Map;

@RequestMapping("/api/dog")
@Controller
public class DogController {
    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }


    @GetMapping({"/", ""})
    @ResponseBody
    public ResponseEntity<Object> findAllDogs(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "birthday", required = false) LocalDate birthday,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "weight", required = false) Float weight,
            @RequestParam(value = "sex", required = false) Sex sex
    ) {
        try {
            return HttpResponse.successOk("All dogs found successfully", dogService.findDogsByAllCriteria(id, name, birthday, breed, weight, sex));
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to fetch dogs: " + e.getMessage(), null);
        }
    }

    @PostMapping({"/", ""})
    @ResponseBody
    public ResponseEntity<Object> createDog(@RequestBody DogDto dogDto) {
        try {
            Map<String, Object> createDogResDtos = dogService.createDog(dogDto);
            return HttpResponse.successCreated("Dog successfully created.", createDogResDtos);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating dog: " + e.getMessage(), null);
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
            return HttpResponse.successOk("Dog deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
        } catch (AccessDeniedException e) {
            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting dog: " + e.getMessage(), null);
        }
    }
}
