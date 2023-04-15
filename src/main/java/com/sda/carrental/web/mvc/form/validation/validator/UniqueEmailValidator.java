package com.sda.carrental.web.mvc.form.validation.validator;

import com.sda.carrental.service.UserService;
import com.sda.carrental.web.mvc.form.validation.constraint.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueEmail constraint) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext cvc) {
        return userService.isEmailUnique(input);
    }
}
