package api.board;


import api.common.util.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/board")
public class PostController {
    private final PostService boardService;

    public PostController(PostService boardService) { this.boardService = boardService }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findAllPosts() {
        try {
            return HttpResponse.successOk();
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @GetMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<Object> findArticleById(@PathVariable Integer articleId) {
        try {

        }
    }

}
