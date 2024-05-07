package api.user.walker;

import api.common.util.http.HttpResponse;
import api.user.dto.WalkerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user/walker")
@Controller
public class WalkerController {
    private final WalkerService walkerService;

    @Autowired
    public WalkerController(WalkerService walkerService) {
        this.walkerService = walkerService;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ResponseEntity<Object> findAllWalkers() {
        List<WalkerDto> walkers = walkerService.findAllWalkers();
        return HttpResponse.successOk("All walkers found successfully", walkers);
    }

    @GetMapping("/{walkerId}")
    @ResponseBody
    public ResponseEntity<Object> findWalkerById(@PathVariable Integer walkerId) {
        WalkerDto walkerDto = walkerService.findWalkerById(walkerId);
        if(walkerDto == null) {
            return HttpResponse.notFound("Error: Walker not found", null);
        }else{
            return HttpResponse.successOk("Walker found successfully", walkerDto);
        }
    }

//    @PatchMapping("/{walkerId}")
//    @ResponseBody
//    public ResponseEntity<Object> updateWalkerById(@PathVariable Integer walkerId) {
//    }

}
