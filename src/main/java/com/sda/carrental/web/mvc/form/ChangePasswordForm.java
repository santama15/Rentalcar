package com.sda.carrental.web.mvc.form;

import com.sda.carrental.web.mvc.form.validation.constraint.CurrentPassword;
import com.sda.carrental.web.mvc.form.validation.constraint.MatchingPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@MatchingPassword(message = "Powtórzenie hasła nie jest zgodne!", targetFormClasses = {ChangePasswordForm.class})
public class ChangePasswordForm {

    @NotEmpty(message = "Pole nie może być puste!")
    @CurrentPassword(message = "Podane hasło nie jest zgodne z obecnym")
    private String currentPassword;

    @Size(min = 8, message = "Minimalna ilość znaków: 8")
    private String newPassword;

    @Size(min = 8, message = "Minimalna ilość znaków: 8")
    private String newPasswordRepeat;
}
