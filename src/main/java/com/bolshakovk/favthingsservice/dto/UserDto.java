package com.bolshakovk.favthingsservice.dto;

import com.bolshakovk.favthingsservice.validation.PasswordMatches;
import com.bolshakovk.favthingsservice.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@PasswordMatches
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {

    private String username;



    private String secondName;


    private String thirdName;





    private String password;


    private String matchingPassword;


    @ValidEmail

    private String email;

    public UserDto(String username, String secondName, String thirdName, String password, String email) {
        this.username = username;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + username + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
