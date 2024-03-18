package api.auth.dto;

import api.user.User;
import api.user.enums.Gender;
import api.user.enums.UserType;

import java.time.LocalDateTime;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
