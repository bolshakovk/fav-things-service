package com.bolshakovk.favthingsservice.security;

import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.utils.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        User userDetails = (User) authentication.getPrincipal();
        System.out.println("Username " + userDetails.getUsername());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities.forEach(auth -> System.out.println(authentication.getAuthorities()));

        String redirectUrl = request.getContextPath();
        if (userDetails.getRoles().contains(Role.USER)){
            redirectUrl = "/main-user";
        }else if(userDetails.getRoles().contains(Role.ADMIN)){
            redirectUrl = "/main-admin";
        }
        response.sendRedirect(redirectUrl);
    }
}
