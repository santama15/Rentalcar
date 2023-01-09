package com.sda.carrental.web.mvc;

import com.sda.carrental.model.users.User;
import com.sda.carrental.service.UserService;
import com.sda.carrental.web.mvc.form.CreateCustomerForm;
import com.sda.carrental.web.mvc.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private static final String MESSAGE_KEY = "message";
    private final UserService userService;






    @GetMapping
    public String create(ModelMap map) {
        map.addAttribute("customerek", new CreateCustomerForm());  //obiekt POJO
        map.addAttribute("roles", User.Roles.values());

        return "registerCustomer";
    }




    //zapisywanie danych do bazy
    @PostMapping
    public String handleCreate(@ModelAttribute("customerek") @Valid CreateCustomerForm form, Errors errors, RedirectAttributes redirectAttributes) {


        if (errors.hasErrors()) {
//            model.addAttribute("roles", User.Roles.values());
            return "registerCustomer";
        }
        userService.save(CustomerMapper.toEntity(form));

        redirectAttributes.addAttribute(MESSAGE_KEY, "Użytkownik " + form.getName() +" "+form.getSurname() + " o loginie "+ form.getEmail() +" został dodany");

        return "redirect:";
    }


}
