package com.bolshakovk.favthingsservice.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
/*
@Component
public class AuthenticationSuccessListenerImpl implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent appEvent) {
        AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        //if (userDetails instanceof UserEntity) {
            // Обрабатываем успешный вход в систему (запись в лог или что там).
            // UserDetailsImpl - это ваша реализация интерфейса UserDetails
            // ...
            System.out.println(userDetails.getUsername() + " : " + userDetails.getPassword() + " " + userDetails.getAuthorities());
        //}
       // else {
            System.out.println(userDetails.getUsername() + " : " + userDetails.getPassword() + " " + userDetails.getAuthorities());
       // }
    }
}*/