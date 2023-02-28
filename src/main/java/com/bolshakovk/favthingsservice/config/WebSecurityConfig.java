package com.bolshakovk.favthingsservice.config;
import com.bolshakovk.favthingsservice.security.JwtConfigurer;
import com.bolshakovk.favthingsservice.security.LoginSuccessHandler;
import com.bolshakovk.favthingsservice.service.impl.UserServiceImpl;
import com.bolshakovk.favthingsservice.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.bolshakovk.favthingsservice.config")
public class  WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;
    @Autowired UserServiceImpl userService;

    @Autowired private LoginSuccessHandler successHandler;
    //ендпоинты, по которым можно ходить без авторизации
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/",
            "/registration",
            "/activate/*"
    };
    //для джвт токена
    public WebSecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                    .antMatchers("/main-admin").hasAuthority(Role.ADMIN.name()) // всего лишь 2 роли, авторизированные могут ходить только на юзер форму и админ, засекьюрил админа
                    .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                    .apply(jwtConfigurer) //добавил джвт конфигурацию тут
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(successHandler)
                    .permitAll()
                .and()
                    .logout().permitAll();
    }
    //аутентификатор принимает сервис пользователя и шифровальщик пароля
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
