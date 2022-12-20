package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateIndexForm {


    private Long branch_id; //dodane przeze mnie dla /index.html

    @NotBlank(message = "Pole nie może być puste")
    private String city;

    @NotBlank(message = "Pole nie może być puste")
    private String address;

    @NotBlank(message = "Pole nie może być puste")
    private LocalDate dateTo;

    @NotBlank(message = "Pole nie może być puste")
    private LocalDate dateCreated;


}
