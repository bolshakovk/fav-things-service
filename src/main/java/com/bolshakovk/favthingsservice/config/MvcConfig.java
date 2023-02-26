package com.bolshakovk.favthingsservice.config;

import com.bolshakovk.favthingsservice.validation.EmailValidator;
import com.bolshakovk.favthingsservice.validation.PasswordMatchesValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
          viewControllerRegistry.addViewController("/login").setViewName("login");
          viewControllerRegistry.addViewController("/registration").setViewName("registration");
          viewControllerRegistry.addViewController("/main-user").setViewName("main-user");
        viewControllerRegistry.addViewController("/main-admin").setViewName("main-admin");
          viewControllerRegistry.addViewController("/").setViewName("welcome");
    }
    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
