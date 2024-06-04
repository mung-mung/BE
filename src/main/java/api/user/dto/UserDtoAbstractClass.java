package api.user.dto;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.UserAccount;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public abstract class UserDtoAbstractClass {
    protected Integer id;
    protected String email;
    protected String userName;
    protected Role role;
    protected String avatarUrl;
    protected String contact;
    protected Gender gender;
    protected LocalDate birthday;
    public UserDtoAbstractClass(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.email = userAccount.getEmail();
        this.userName = userAccount.getUserName();
        this.role = userAccount.getRole();
        this.avatarUrl = userAccount.getAvatarUrl();
        this.contact = userAccount.getContact();
        this.gender = userAccount.getGender();
        this.birthday = userAccount.getBirthday();
    }
}
