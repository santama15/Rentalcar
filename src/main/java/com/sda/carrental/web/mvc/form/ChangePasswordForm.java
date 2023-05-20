package com.sda.carrental.web.mvc.form;

import com.sda.carrental.web.mvc.form.validation.constraint.CurrentPassword;
import com.sda.carrental.web.mvc.form.validation.constraint.MatchingPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@MatchingPassword(message = "Provided new password fields does not match", targetFormClasses = {ChangePasswordForm.class})
public class ChangePasswordForm {

    @NotEmpty(message = "Field cannot be empty")
    @CurrentPassword(message = "Provided current password is incorrect")
    private String currentPassword;

    @Size(min = 8, message = "Minimum length: 8")
    private String newPassword;

    @Size(min = 8, message = "Minimum length: 8")
    private String newPasswordRepeat;
}
