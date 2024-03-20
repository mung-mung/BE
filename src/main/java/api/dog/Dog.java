package api.dog;

import api.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "dogs")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogId;

    @Column(name = "DOG_NAME", nullable = false)
    private String dogName;

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID"),
    })
    private User owner;

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "WALKER_ID", referencedColumnName = "ID", nullable = true),
    })
    private User walker;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Dog(String name, User owner) {
        this.dogName = name;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + dogId +
                ", dogName='" + dogName + '\'' +
                ", owner='" + owner + '\'' +
                ", walker='" + walker + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    //현재 walker 등록
    public void setWalker(User walker){
        this.walker = walker;
    }

    //walker 삭제
    public void removeWalker(){
        this.walker = null;
    }

}
