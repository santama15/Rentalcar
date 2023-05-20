package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangeContactForm {
    @NotBlank(message = "Field cannot be empty")
    @Size(min=7, max=15, message="Incorrect contact number size")
    @Digits(integer = 15, fraction = 0, message="Incorrect contact number format")
    private String contactNumber;
}
