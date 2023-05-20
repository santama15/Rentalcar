package com.sda.carrental.web.mvc.form;

import com.sda.carrental.constants.enums.Country;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangeAddressForm {
    private Country country;

    @NotBlank(message = "Field cannot be empty")
    private String city;

    @NotBlank(message = "Field cannot be empty")
    private String address;
}
