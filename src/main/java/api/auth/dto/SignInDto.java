package api.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignInDto {
    private String email;
    private String pw;

    public SignInDto(String email, String pw){
        this.email = email;
        this.pw = pw;
    }

}
