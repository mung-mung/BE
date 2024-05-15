package api.follow;

import api.common.util.http.HttpResponse;
import api.follow.dto.FollowDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RequestMapping("/api/follow")
@Controller
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping({"/",""})
    @ResponseBody
    public ResponseEntity<Object> createFollow(@RequestBody FollowDto followDto) {
        try {
            Follow createfollowDto = followService.createFollow(followDto);
            return HttpResponse.successOk("Follow created", createfollowDto);
        }catch (Exception e){
            return HttpResponse.badRequest("Error creating follow: "+ e.getMessage(), null);
        }
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Object> findFollows(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "followerId", required = false) Integer followerId,
            @RequestParam(value = "followeeId", required = false) Integer followeeId){
        List<FollowDto> followDtos = followService.findFollows(id, followerId, followeeId);
        if(followDtos.isEmpty()){
            return HttpResponse.notFound("No follows found matching criteria", null);
        }else {
            return HttpResponse.successOk("Follows found successfully", followDtos);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFollow(@PathVariable Integer id){
        try{
            followService.deleteFollowById(id);
            return HttpResponse.successOk("Follow deleted successfully", null);
        }catch (EntityNotFoundException e){
            return HttpResponse.notFound(e.getMessage(), null);
        }catch (AccessDeniedException e){
            return HttpResponse.notFound(e.getMessage(), null);
        }catch (Exception e){
            return HttpResponse.internalError("Error in deleteFollow : " + e.getMessage(), null);
        }
    }

}
