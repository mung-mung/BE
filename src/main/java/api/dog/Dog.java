package api.dog;

import api.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
            @JoinColumn(name = "OWNER_EMAIL", referencedColumnName = "EMAIL")
    })
    private User owner;

    @ManyToOne()
    @JoinColumns({
            @JoinColumn(name = "WALKER_ID", referencedColumnName = "ID"),
            @JoinColumn(name = "WALKER_EMAIL", referencedColumnName = "EMAIL")
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

    public Long getId() {
        return dogId;
    }

    public void setId(Long id) {
        this.dogId = id;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getWalker() {
        return walker;
    }

    public void setWalker(User walker) {
        this.walker = walker;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
