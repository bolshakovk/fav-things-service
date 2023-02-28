package com.bolshakovk.favthingsservice.controllers;

import com.bolshakovk.favthingsservice.entity.Fav;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.service.FavService;
import com.bolshakovk.favthingsservice.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@AllArgsConstructor
@Api(value = "Контроллер авторизованного юзера")
public class FavUserPageController {
    private final FavService favService;
    private final UserServiceImpl userService;
    @ApiOperation(value = "Апи для загрузки страницы пользователя")
    @GetMapping(value="/main-user")
    public String initUser(){
        return "main-user";
    }
    @ApiOperation(value = "Апи для загрузки страницы админа и просмотра юзеров")
    @GetMapping(value="/main-admin")
    public String initAdmin(Map<String, Object> model){
        userService.initModel(model);
        return "main-admin";
    }


    @ApiOperation(value = "Апи для загрузки предпочтений юзера")
    @PostMapping(value="/main-user")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam(name="song") String song,
            @RequestParam(name="dish") String dish,
            @RequestParam(name="color") String color,
            @RequestParam(name="date") String date,
            @RequestParam(name="digit") Integer digit) {



        Fav favEntity = new Fav(dish, song, digit, date, color, user);
        favService.save(favEntity);
        return "main-user";
    }
    @ApiOperation(value = "Апи для загрузки предпочтений админа")
    @PostMapping(value="/main-admin")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam(name="song") String song,
            @RequestParam(name="dish") String dish,
            @RequestParam(name="color") String color,
            @RequestParam(name="date") String date,
            @RequestParam(name="digit") Integer digit,
            Model model)  {

        Fav favEntity = new Fav(dish, song, digit, date, color, user);
        favService.save(favEntity);
        return "main-admin";
    }
}
