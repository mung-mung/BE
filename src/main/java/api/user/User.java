package api.user;

import api.dog.Dog;
import api.user.enums.Gender;
import api.user.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@NoArgsConstructor
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "PW", nullable = false)
    private String pw;

    @Column(name = "AVATAR_URL", nullable = false)
    private String avatarUrl = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";

    @Column(name = "CONTACT", nullable = false)
    private String contact;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDateTime birthday;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Dog> ownedDogs = new ArrayList<>();

    @OneToMany(mappedBy = "walker", cascade = CascadeType.ALL)
    private List<Dog> walkedDogs = new ArrayList<>();


    public User(String email, UserType userType, String pw, String contact, Gender gender, LocalDateTime birthday) {
        this.email = email;
        this.userType = userType;
        this.pw = pw;
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", contact='" + contact + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}