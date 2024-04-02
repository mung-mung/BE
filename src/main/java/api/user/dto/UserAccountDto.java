package api.user.dto;

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
}
