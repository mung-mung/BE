package api.board.dto;

import lombok.*;
import api.board.post;
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private String title;
    private String content;

    public PostResponseDto(post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}