package api.article.dto;

import api.article.Article;
import api.article.ArticleContractDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArticleDto {
    private Integer id;
    private String title;
    private ArticleContractDetail articleContractDetail;
    private Integer ownerId;

    public ArticleDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.articleContractDetail = article.getArticleContractDetail();
        this.ownerId = article.getOwner().getId();
    }



}
