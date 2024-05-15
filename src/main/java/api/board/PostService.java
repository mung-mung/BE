package api.board;

import api.board.dto.PostRegisterDto;
import api.board.dto.PostResponseDto;
import api.user.userAccount.*;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;

    public PostService(PostRepository postRepository, UserAccountRepository userAccountRepository) {
        this.postRepository = postRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional
    public List<post> getAllPost() {
        return postRepository.findAll();
    }

    @Transactional
    public PostResponseDto getPostById(Integer id) {
        post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid post ID"));
        return new PostResponseDto(post);
    }

    @Transactional
    public post createPost(PostRegisterDto post) {
        if (post.getWriterId() == null) {
            throw new IllegalArgumentException("writer ID must not be null");
        }

        UserAccount writer = userAccountRepository.findById(post.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("user is not found"));
        api.board.post newPost = new post(post.getTitle(), post.getContent(), writer);
        postRepository.save(newPost);

        return postRepository.save(post.toEntity(writer));
    }

    @Transactional
    public post updatePost(Integer id, PostRegisterDto updatedPost) {
        post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        post.update(updatedPost.getTitle(), updatedPost.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

}
