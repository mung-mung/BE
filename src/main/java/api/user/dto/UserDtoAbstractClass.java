package api.user.dto;

import api.user.enums.Role;
import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public abstract class UserDtoAbstractClass {
    private Integer id;
    private String email;
    private Role role;
    private String avatarUrl;
    private String contact;
    private String gender;
    private LocalDate birthday;
    public UserDtoAbstractClass(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.email = userAccount.getEmail();
        this.role = userAccount.getRole();
        this.avatarUrl = userAccount.getAvatarUrl();
        this.contact = userAccount.getContact();
        this.gender = userAccount.getGender().toString();
        this.birthday = userAccount.getBirthday();
    }
}
