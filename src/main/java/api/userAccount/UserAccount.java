package api.userAccount;

import api.dog.Dog;
import api.userAccount.enums.Gender;
import api.userAccount.enums.Role;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name="user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "ROLE", nullable = false)
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
    private LocalDateTime birthday;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Dog> ownedDogs = new ArrayList<>();

    @OneToMany(mappedBy = "walker", cascade = CascadeType.ALL)
    private List<Dog> walkedDogs = new ArrayList<>();


    public UserAccount(String email, Role role, String pw, String contact, Gender gender, LocalDateTime birthday) {
        this.email = email;
        this.role = role;
        this.pw = pw;
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}