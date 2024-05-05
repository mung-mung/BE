package api.user.userAccount;

import api.user.enums.Gender;
import api.user.enums.Role;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ROLE", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name="user_account")
public abstract class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "ROLE", nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

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
    private LocalDate birthday;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;


    public UserAccount(String email, Role role, String pw, String contact, Gender gender, LocalDate birthday) {
        this.email = email;
        this.role = role;
        this.pw = pw;
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
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