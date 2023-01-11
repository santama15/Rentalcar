package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class CreateIndexForm {

    private Long branch_id_from;

    private Long branch_id_to;

    private boolean isFirstBranch;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    private LocalDate dateCreated;
}
