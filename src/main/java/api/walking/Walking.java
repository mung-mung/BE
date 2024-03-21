package api.walking;

import api.dog.Dog;
import api.user.walker.Walker;
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
