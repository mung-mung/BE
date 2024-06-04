package api.user.dto;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserAccountDto extends UserDtoAbstractClass{
    public UserAccountDto() {}
    public UserAccountDto(UserAccount userAccount) {
        super(userAccount);
    }
}
