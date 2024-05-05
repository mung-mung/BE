package api.user.dto;

import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Setter
@Getter
public class UserAccountDto {
    private String email;
    private String avatarUrl;
    private String pw;
    private String contact;
    private String gender;
    private LocalDate birthday;
    public UserAccountDto(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.avatarUrl = userAccount.getAvatarUrl();
        this.pw = null;
        this.contact = userAccount.getContact();
        this.gender = userAccount.getGender().toString();
        this.birthday = userAccount.getBirthday();
    }
}
