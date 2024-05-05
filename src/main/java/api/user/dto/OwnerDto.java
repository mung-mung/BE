package api.user.dto;

import api.user.owner.Owner;
import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class OwnerDto {
    private String email;
    private String avatarUrl;
    private String pw;
    private String contact;
    private String gender;
    private LocalDate birthday;
    // 추후에 Owner 전용 필드 추가 예정
    public OwnerDto(Owner owner) {
        this.email = owner.getEmail();
        this.avatarUrl = owner.getAvatarUrl();
        this.pw = null;
        this.contact = owner.getContact();
        this.gender = owner.getGender().toString();
        this.birthday = owner.getBirthday();
    }
}
