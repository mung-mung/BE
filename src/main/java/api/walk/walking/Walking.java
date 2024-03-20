package api.walk.walking;

import api.dog.Dog;
import api.walk.walker.Walker;
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
@Entity
@Table(name="walking")
public class Walking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WALKER_ID", nullable = false)
    private Walker walker;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOG_ID", nullable = false)
    private Dog dog;
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Walking(Walker walker, Dog dog){
        this.walker = walker;
        this.dog = dog;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
