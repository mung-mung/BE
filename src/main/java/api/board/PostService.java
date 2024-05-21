package api.board;

import api.board.dto.PostDto;
import api.user.userAccount.*;


import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
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
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Transactional
    public PostDto getPostById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid post ID"));
        return new PostDto(post);
    }

    @Transactional
    public PostDto createPost(PostDto post) {
        if (post.getWriterId() == null) {
            throw new IllegalArgumentException("writer ID must not be null");
        }

        UserAccount writer = userAccountRepository.findById(post.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("user is not found"));
        Post newPost = new Post(post.getTitle(), post.getContent(), writer);
        Post savedPost = postRepository.save(newPost);

        return new PostDto(savedPost);
    }

//    public Post createPost(PostRegisterDto postDto) throws AccessDeniedException {
//        // 현재 로그인된 사용자 정보 가져오기
//        UserAccountDto loggedInUserAccountDto = LoggedInUser.getLoggedInUserAccountDto();
//
//        // 사용자 정보가 없으면 예외 발생
//        if (loggedInUserAccountDto == null) {
//            throw new AccessDeniedException("User must be logged in to create a post.");
//        }
//
//        // UserAccountDto에서 UserAccount 객체 조회
//        UserAccount writer = userAccountRepository.findByEmail(loggedInUserAccountDto.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        // 새 게시물 생성 및 저장
//        Post newPost = new Post(postDto.getTitle(), postDto.getContent(), loggedInUserAccountDto);
//
//        postRepository.save(newPost);
//
//        return postRepository.save(postDto.toEntity(writer));
//    }


    @Transactional
    public PostDto updatePost(Integer id, PostDto updatedPost) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        post.update(updatedPost.getTitle(), updatedPost.getContent());

        Post savedPost = postRepository.save(post);

        return new PostDto(savedPost);
    }

    @Transactional
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

}
