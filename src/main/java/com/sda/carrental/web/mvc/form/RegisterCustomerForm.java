package com.sda.carrental.web.mvc.form;

import com.sda.carrental.constants.enums.Country;
import com.sda.carrental.web.mvc.form.validation.constraint.MatchingPassword;
import com.sda.carrental.web.mvc.form.validation.constraint.UniqueEmail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@MatchingPassword(message = "Powtórzenie hasła nie jest zgodne!", targetFormClasses = {RegisterCustomerForm.class})
public class RegisterCustomerForm {

    @NotBlank(message = "Pole nie może być puste")
    private String name;

    @NotBlank(message = "Pole nie może być puste")
    private String surname;

    @NotBlank(message = "Pole nie może być puste")
    @Size(min=7, max=15, message="Nieprawidłowa długość numeru kontaktowego")
    @Digits(integer = 15, fraction = 0)
    private String contactNumber;

    private Country country;

    @NotBlank(message = "Pole nie może być puste")
    private String city;

    @NotBlank(message = "Pole nie może być puste")
    private String address;

    @NotBlank(message = "Pole nie może być puste")
    @Email(message = "Login powinien być poprawnym formatem adresu e-mail")
    @UniqueEmail(message = "Podany login jest zajęty.")
    private String email;

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 8, message = "Minimalna ilość znaków: 8")
    private String password;

    @NotBlank(message = "Pole nie może być puste")
    @Size(min = 8, message = "Minimalna ilość znaków: 8")
    private String confirmPassword;
}
