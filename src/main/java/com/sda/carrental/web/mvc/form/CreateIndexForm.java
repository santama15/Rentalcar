package com.sda.carrental.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class CreateIndexForm {

    private Long branch_id_from;

    private Long branch_id_to;

    private boolean firstBranchChecked;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    private LocalDate dateCreated;

    //trzeba sprawdzić jakoś czy dateFrom jest przed dateTo
    //i wstawić wizualnie errory w html'u
}
