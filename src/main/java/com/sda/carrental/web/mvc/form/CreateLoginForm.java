package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreateLoginForm {
//todo?
    @NotBlank(message = "Pole nie może być puste")
    @Email(message = "Login powinien być poprawnym formatem adresu e-mail")
    private String email;

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 4, message = "Minimalna ilość znaków: 4")
    private String password;

}
