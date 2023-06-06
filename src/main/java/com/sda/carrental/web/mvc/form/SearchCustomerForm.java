package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SearchCustomerForm {

    @NotBlank(message = "Field 'department' cannot be empty")
    private Long departmentId;

    private String name;

    private String surname;
}
