package api.article.dto;

import api.article.ArticleContractDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateContractDto {
    private Integer dogId;
    private ArticleContractDetail articleContractDetail;
}
