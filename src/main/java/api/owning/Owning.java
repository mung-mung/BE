package api.owning;

import api.dog.Dog;
import api.user.owner.Owner;
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
@Table(name="owning")
public class Owning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Owner owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOG_ID", nullable = false)
    private Dog dog;
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Owning(Owner owner, Dog dog){
        this.owner = owner;
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
