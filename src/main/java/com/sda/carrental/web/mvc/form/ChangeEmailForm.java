package com.sda.carrental.web.mvc.form;

import com.sda.carrental.web.mvc.form.validation.constraint.CurrentEmail;
import com.sda.carrental.web.mvc.form.validation.constraint.MatchingEmail;
import com.sda.carrental.web.mvc.form.validation.constraint.UniqueEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@MatchingEmail(message = "Powtórzenie email'u nie jest zgodne")
public class ChangeEmailForm {

    @NotEmpty(message = "Pole nie może być puste!")
    @CurrentEmail
    private String currentEmail;

    @NotEmpty(message = "Pole nie może być puste!")
    @Email(message = "Login powinien być poprawnym formatem adresu e-mail")
    @UniqueEmail(message = "Podany login jest zajęty.")
    private String newEmail;

    @NotEmpty(message = "Pole nie może być puste!")
    @Email(message = "Login powinien być poprawnym formatem adresu e-mail")
    private String newEmailRepeat;
}
