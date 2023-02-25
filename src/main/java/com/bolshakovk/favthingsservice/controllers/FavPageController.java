package com.bolshakovk.favthingsservice.controllers;

import com.bolshakovk.favthingsservice.entity.Fav;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.service.FavService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@AllArgsConstructor
@Api(value = "Контроллер авторизованного юзера")
public class FavPageController {
    private final FavService favService;
    @GetMapping(value="/")
    public String greeting(){
        return "welcome";
    }
    @GetMapping(value="/main")
    public String initg(){
        return "main";
    }

    @PostMapping(value="/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam(name="song") String song,
            @RequestParam(name="dish") String dish,
            @RequestParam(name="color") String color,
            @RequestParam(name="date") String date,
            @RequestParam(name="digit") Integer digit,
            Model model) throws ParseException {



        Fav favEntity = new Fav(dish, song, digit, date, color, user);
        System.out.println(favEntity);
        favService.save(favEntity);
        return "main";
    }
}
