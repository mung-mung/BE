package api.auth.dto;

import api.user.enums.Gender;
import api.user.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String email;
    private UserType userType;
    private String pw;
    private String contact;
    private Gender gender;
    private LocalDateTime birthday;

    public SignUpDto(String email, UserType userType, String pw, String contact, Gender gender, LocalDateTime birthday){
        this.email = email;
        this.userType = userType;
        this.pw = pw;
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
    }

}
