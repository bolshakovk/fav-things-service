package com.bolshakovk.favthingsservice.dto;

import lombok.Data;

@Data
public class AuthentificationRequestDto {
    private String username;
    private String password;
}
