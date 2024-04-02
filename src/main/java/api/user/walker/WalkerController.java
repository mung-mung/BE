package api.user.walker;

import api.common.util.http.HttpResponse;
import api.dog.Dog;
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

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findAllWalkers() {
    }

    @GetMapping("/{walkerId}")
    @ResponseBody
    public ResponseEntity<Object> findWalkerById(@PathVariable Integer walkerId) {
    }

    @PatchMapping("/{walkerId}")
    @ResponseBody
    public ResponseEntity<Object> updateWalkerById(@PathVariable Integer walkerId) {
    }

}
