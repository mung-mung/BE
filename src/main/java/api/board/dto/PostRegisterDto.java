package api.board.dto;

import lombok.*;
import api.board.Post;
import api.user.userAccount.UserAccount;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRegisterDto {
    private String title;
    private String content;
    private Integer writerId;

    public Post toEntity(UserAccount writer) {
        return new Post(title, content, writer);
    }
}