package api.board.dto;

import lombok.*;
import api.board.post;
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

    public post toEntity(UserAccount writer) {
        return new post(title, content, writer);
    }
}