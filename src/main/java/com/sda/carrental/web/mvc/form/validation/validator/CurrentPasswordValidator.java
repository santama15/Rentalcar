package com.sda.carrental.web.mvc.form.validation.validator;

import com.sda.carrental.service.CustomerService;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.validation.constraint.CurrentPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrentPasswordValidator implements ConstraintValidator<CurrentPassword, String> {

    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(CurrentPassword constraint) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext cvc) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerService.isCurrentPassword(cud, input);
    }
}
