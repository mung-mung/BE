package api.article.article;

import api.article.enums.ArticleTypes;
import api.user.userAccount.UserAccount;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ARTICLE_TYPE", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name="article")
public abstract class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private UserAccount writer;

    @Column(name = "ARTICLE_TYPE", nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ArticleTypes articleType;


    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Article(UserAccount writer, ArticleTypes articleType) {
        this.writer = writer;
        this.articleType = articleType;
    }


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }
}
