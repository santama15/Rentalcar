package com.sda.carrental.web.mvc;

import com.sda.carrental.model.users.User;
import com.sda.carrental.service.UserService;
import com.sda.carrental.web.mvc.form.CreateCustomerForm;
import com.sda.carrental.service.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class UserController {
    private static final String MESSAGE_KEY = "message";
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.addAttribute("customer", new CreateCustomerForm());
        map.addAttribute("roles", User.Roles.values());

        return "user/registerCustomer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleCreate(@ModelAttribute("customer") @Valid CreateCustomerForm form, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            //TODO handle validation and create message here
            return "user/registerCustomer";
        }
        userService.save(CustomerMapper.toEntity(form));
        redirectAttributes.addAttribute(MESSAGE_KEY, "Użytkownik " + form.getName() + " " + form.getSurname() + " o loginie " + form.getEmail() + " został dodany");

        return "login";
    }


}
