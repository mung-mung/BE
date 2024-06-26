package api.user.owner;

import api.user.userAccount.UserAccount;
import api.user.enums.Gender;
import api.user.enums.Role;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Getter
@DiscriminatorValue("OWNER")
@Entity
@Table(name="owner")
public class Owner extends UserAccount{
    public Owner(String email, String userName, Role role, String pw, String contact, Gender gender, LocalDate birthday) {
        super(email, userName, role, pw, contact, gender, birthday);
    }
}
