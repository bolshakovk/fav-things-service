package com.bolshakovk.favthingsservice.controllers;

import com.bolshakovk.favthingsservice.dto.AuthentificationRequestDto;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.repository.UserRepository;
import com.bolshakovk.favthingsservice.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/vi/auth")
@Api(value = "апи для авторизации")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Апи для аутентификации")
    public ResponseEntity<?> authenticate(@RequestBody AuthentificationRequestDto authentificationRequestDto){
        try {
            String username = authentificationRequestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authentificationRequestDto.getPassword()));
            User user = userRepository.findByUsername(authentificationRequestDto.getUsername());
            String token = jwtTokenProvider.createToken(authentificationRequestDto.getUsername(), user.getRoles().toString());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", authentificationRequestDto.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("invalid combination of creds", HttpStatus.FORBIDDEN);
        }
    }
    @ApiOperation(value = "Апи логаута")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
