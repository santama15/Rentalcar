package com.sda.carrental.web.mvc.form;

import com.sda.carrental.web.mvc.form.validation.constraint.CurrentPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class DeleteAccountForm {

    @NotEmpty(message = "Pole nie może być puste!")
    @CurrentPassword(message = "Podane hasło nie jest zgodne z obecnym")
    private String currentPassword;
}
