package com.sda.carrental.web.mvc.form;

import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreateCustomerForm {

    private Long id;

    @NotBlank(message = "Pole nie może być puste")
    private String name;

    @NotBlank(message = "Pole nie może być puste")
    private String surname;

    @NotBlank(message = "Pole nie może być puste")
    private String address;

    @NotBlank(message = "Pole nie może być puste")
    @Email(message = "Login powinien być poprawnym formatem adresu e-mail")
    private String email;

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 4, message = "Minimalna ilość znaków: 4")
    private String password;

//TODO
//    private User.Roles = ;
}
