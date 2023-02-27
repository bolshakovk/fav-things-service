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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(name="firstName") String firstName,
            @RequestParam(name="secondName") String secondName,
            @RequestParam(name="thirdName") String thirdName,
            @RequestParam(name="password") String password,
            @RequestParam(name="birth") String birth,
            @RequestParam(name="role") String role,
            Map<String, Object> model)
    {
        UserDto userDto = new UserDto(username, secondName, thirdName, password, email, role, firstName, birth);
        if (!userService.registerNewUserAccount(userDto)){
            model.put("message", "user exist");
        }

        userService.registerNewUserAccount(userDto);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    @ApiOperation(value = "Апи для подтверждения пользователя кодом из почты")
    public String activate(Model model, @PathVariable String code){
        boolean isActive = userService.activateUser(code);
        System.out.println("clicked link!");
        System.out.println(userService.findAll());
        if (isActive){
            System.out.println("юзер активирован");
            model.addAttribute("message:", "Юзер активирован");
        }else {
            System.out.println("юзер не найден");
            model.addAttribute("message:", "Код не найден");
        }
        return "login";
    }
}
