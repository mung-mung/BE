package api.auth.dto;

import api.user.userAccount.enums.Gender;
import api.user.userAccount.enums.Role;
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

    public SignUpDto(String email, Role role, String pw, String contact, Gender gender, LocalDate birthday){
        this.email = email;
        this.role = role;
        this.pw = pw;
        this.contact = contact;
        this.gender = gender;
        this.birthday = birthday;
    }

}
