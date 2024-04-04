package api.auth.dto;

import api.user.enums.Gender;
import api.user.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String email;
    private Role role;
    private String pw;
    private String contact;
    private Gender gender;
    private LocalDate birthday;
    private String avatarUrl = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";

    public SignUpDto(String email, Role role, String pw, String avatarUrl, String contact, Gender gender, LocalDate birthday){
        this.email = email;
        this.role = role;
        this.pw = pw;
        setAvatarUrl(avatarUrl);
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
    }

    public void setAvatarUrl(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.isEmpty()) {
            this.avatarUrl = "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png";
        } else {
            this.avatarUrl = avatarUrl;
        }
    }
}
