package com.sda.carrental.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String loginPage(final ModelMap map) {
        return "login";
    }

}
