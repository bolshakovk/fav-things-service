package com.bolshakovk.favthingsservice.controllers;

import com.bolshakovk.favthingsservice.dto.UserDto;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.repository.UserRepository;
import com.bolshakovk.favthingsservice.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Controller
@AllArgsConstructor
@Api(value = "Контроллер регистрации")
public class Registration {
    @Autowired
    private UserServiceImpl  userService;
    @GetMapping("/registration")
    @ApiOperation(value = "Апи для первоначальной инициализации")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    @ApiOperation(value = "Апи для регистрации")
    public String addUser(
            @RequestParam(name="email") String email,
            @RequestParam(name="username") String username,
            @RequestParam(name="secondName") String secondName,
            @RequestParam(name="thirdName") String thirdName,
            @RequestParam(name="password") String password,
            @RequestParam(name="role") String role,
            @RequestParam(name="birth") String birth,
            Map<String, Object> model) {
        UserDto userDto = new UserDto(
                username,
                secondName,
                thirdName,
                password,
                email,
                role,
                birth);
                //user.getBirth());
        //User userFromDb = userRepository.findByUsername(user.getUsername());
        //if (userFromDb != null) {
            model.put("message", "User exists!");
        //    return "registration";
       // }
       // System.out.println("user from db: " + userFromDb);
        System.out.println("user dto: " + userDto);
        userService.registerNewUserAccount(userDto);
        return "redirect:/login";
    }
}
