package api.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostCreateDto {
    private String title;
    private String content;

    public PostCreateDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
