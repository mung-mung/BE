package api.dog;

import api.dog.enums.Sex;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name="dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDate birthday;

    @Column(name = "BREED", nullable = false, length = 20)
    private String breed;

    @Column(name = "WEIGHT", nullable = false)
    private Float weight;

    @Column(name = "SEX", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "PHOTO_URL", nullable = false)
    private String photoUrl = "https://cdn.pixabay.com/photo/2018/05/26/18/06/dog-3431913_1280.jpg";

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    public Dog(String name, LocalDate birthday, String breed, Float weight, Sex sex){
        this.name = name;
        this.birthday = birthday;
        this.breed = breed;
        this.weight = weight;
        this.sex = sex;
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

