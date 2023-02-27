package com.bolshakovk.favthingsservice.dto;

import com.bolshakovk.favthingsservice.validation.PasswordMatches;
import com.bolshakovk.favthingsservice.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.util.Date;

@Data
@PasswordMatches
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {

    private String username;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String password;
    private String matchingPassword;
    private String role;
    private String birth;
    @ValidEmail
    private String email;

    public UserDto(String username, String secondName, String thirdName, String password, String email, String role, String firstName, String birth) {
        this.username = username;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.role = role;
        this.birth = birth;
    }
}
