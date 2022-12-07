package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateIndexForm {


    private Long branch_id; //dodane przeze mnie dla /index.html

//    @NotBlank(message = "Pole nie może być puste")
    private String city;

//    @NotBlank(message = "Pole nie może być puste")
    private String address;


}
