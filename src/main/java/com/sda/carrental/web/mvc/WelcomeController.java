package com.sda.carrental.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sda.carrental.model.users.User;
import com.sda.carrental.service.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc")
public class WelcomeController
{
    private final UserService userService;
    @GetMapping
    public String home(ModelMap map) {
        User user = userService.findById(2L);
       // map.addAttribute("user", user);
        map.addAttribute("userName", "Jan Kowalski");
        return "home";}
}
