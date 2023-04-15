package com.sda.carrental.web.mvc;

import com.sda.carrental.model.users.User;
import com.sda.carrental.service.UserService;
import com.sda.carrental.web.mvc.form.RegisterCustomerForm;
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
public class RegisterController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String create(ModelMap map) {
        map.addAttribute("customer", new RegisterCustomerForm());
        map.addAttribute("roles", User.Roles.values());

        return "user/registerCustomer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleCreate(@ModelAttribute("customer") @Valid RegisterCustomerForm form, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "user/registerCustomer";
        }
        userService.save(CustomerMapper.toEntity(form));
        redirectAttributes.addAttribute("message", "Użytkownik " + form.getName() + " " + form.getSurname() + " o loginie " + form.getEmail() + " został dodany");

        return "core/login";
    }


}
