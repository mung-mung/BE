package api.user.dto;

import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class WalkerDto {
    private String email;
    private String avatarUrl;
    private String pw;
    private String contact;
    private String gender;
    private LocalDate birthday;
    // 추후에 Walker 전용 필드 추가 예정
    public WalkerDto(UserAccount userAccount) {
        this.email = userAccount.getEmail();
        this.avatarUrl = userAccount.getAvatarUrl();
        this.pw = null;
        this.contact = userAccount.getContact();
        this.gender = userAccount.getGender().toString();
        this.birthday = userAccount.getBirthday();
    }
}
