package api.user.userAccount.dto;

import api.user.dto.UserDtoAbstractClass;
import api.user.userAccount.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccountDto extends UserDtoAbstractClass {
    public UserAccountDto() {}
    public UserAccountDto(UserAccount userAccount) {
        super(userAccount);
    }
}
