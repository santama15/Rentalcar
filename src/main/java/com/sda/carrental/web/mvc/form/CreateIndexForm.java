package com.sda.carrental.web.mvc.form;

import com.sda.carrental.web.mvc.form.validation.constraint.CorrectChronology;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@CorrectChronology(message = "Nieprawidłowa kolejność dat!")
public class CreateIndexForm {

    private Long branch_id_from;

    private Long branch_id_to;

    private boolean firstBranchChecked;

    @FutureOrPresent(message = "Data wypożyczenia samochodu jest przestarzała!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @FutureOrPresent(message = "Data oddania samochodu jest przestarzała!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    private LocalDate dateCreated;

}
