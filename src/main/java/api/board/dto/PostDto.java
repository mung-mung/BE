package api.board.dto;

import api.board.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private Integer writerId;

    // Constructor for creation and update
    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerId = post.getWriter().getId();
    }

    // Constructor for registration
    public PostDto(String title, String content, Integer writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }



}
