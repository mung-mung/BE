package api.board;

import api.board.dto.PostRegisterDto;
import api.board.dto.PostResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody PostRegisterDto postDto) {
        Post post = postService.createPost(postDto);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody PostRegisterDto updatedPost) {
        return postService.updatePost(id, updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }
}
