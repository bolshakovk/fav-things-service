package com.bolshakovk.favthingsservice.dto;

import lombok.Data;

//для отображения на фронте
@Data
public class UserForModel {
    private String fName;
    private String sName;
    private String tName;
    private String birth;
    private String email;
    private String role;

    public UserForModel(String fName, String sName, String tName, String birth, String email, String role) {
        this.fName = fName;
        this.sName = sName;
        this.tName = tName;
        this.birth = birth;
        this.email = email;
        this.role = role;
    }
}
