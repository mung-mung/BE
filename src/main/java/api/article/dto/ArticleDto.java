package api.article.dto;

import api.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArticleDto {
    private Integer id;
    private String title;
    private String content;
    private Integer writerId;

    // Constructor for creation and update
    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writerId = article.getWriter().getId();
    }

    // Constructor for registration
    public ArticleDto(String title, String content, Integer writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }



}
