package api.user.admin;

import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.userAccount.UserAccount;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
@Getter
@DiscriminatorValue("ADMIN")
@Entity
@Table(name="admin")
public class Admin extends UserAccount {
    public Admin(String email,String userName, Role role, String pw, String contact, Gender gender, LocalDate birthday) {
        super(email, userName, role, pw, contact, gender, birthday);
    }
}
