package api.user;

import api.dog.Dog;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "users")
    private List<Dog> dogs = new ArrayList<>();

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
        return Objects.hash(id, email);
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

    public enum UserType {
        ADMIN, OWNER, WALKER
    }

    public enum Gender {
        MALE, FEMALE, PREFER_NOT_TO_DISCOLSE
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPw() {
        return pw;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getContact() {
        return contact;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}