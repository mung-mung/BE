package api.user.dto;

import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class UserAccountDto extends UserDtoAbstractClass{
    public UserAccountDto(UserAccount userAccount) {
        super(userAccount);
    }
}
