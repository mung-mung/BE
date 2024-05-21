package api.board;

import api.board.dto.PostDto;
import api.common.util.http.HttpResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/board")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Object> getAllPost(){
        try {
            return HttpResponse.successOk("All posts found successfully", postService.getAllPost());
        } catch (Exception e) {
            return HttpResponse.internalError("Failed to fetch posts: " + e.getMessage(), null);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> createPost(@RequestBody PostDto postDto) {
        try {
            PostDto post = postService.createPost(postDto);
            return HttpResponse.successCreated("Post successfully created.", post);
        } catch (Exception e) {
            return HttpResponse.badRequest("Error creating dog: " + e.getMessage(), null);
        }
    }

    @GetMapping("/{postId}")
    @ResponseBody
        public ResponseEntity<Object> getPostById(@PathVariable Integer postId) {
        try {
            PostDto foundPost = postService.getPostById(postId);

            if (foundPost != null) {
                return HttpResponse.successOk("Post found successfully", foundPost);
            } else {
                return HttpResponse.notFound("Post not found with ID: " + postId, null);
            }
        } catch (Exception e) {
            return HttpResponse.internalError("Error finding Post: " + e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> updatePost(@PathVariable Integer id, @RequestBody PostDto updatedPost) {
        PostDto postDto = postService.updatePost(id, updatedPost);
        if (postDto != null) {
            return HttpResponse.successOk("Post successfully updated", postDto);
        } else {
            return HttpResponse.notFound("Post not found", null);
        }
    }

    @DeleteMapping("/{postId}")
    @ResponseBody
    public ResponseEntity<Object> deletePost(@PathVariable Integer postId) {
        try {
            postService.deletePost(postId);
            return HttpResponse.successOk("Post deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return HttpResponse.notFound(e.getMessage(), null);
//        } catch (AccessDeniedException e) {
//            return HttpResponse.forbidden(e.getMessage(), null);
        } catch (Exception e) {
            return HttpResponse.internalError("Error deleting post: " + e.getMessage(), null);
        }
    }
}
