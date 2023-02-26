package com.bolshakovk.favthingsservice.utils;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
     USER, ADMIN;

     @Override
     public String getAuthority() {
          return name();
     }
}
