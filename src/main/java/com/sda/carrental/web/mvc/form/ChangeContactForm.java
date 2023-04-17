package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangeContactForm {
    @NotBlank(message = "Pole nie może być puste")
    @Size(min=7, max=15, message="Nieprawidłowa długość numeru kontaktowego")
    @Digits(integer = 15, fraction = 0)
    private String contactNumber;
}
