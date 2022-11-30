package com.sda.carrental.model.operating;

import java.time.LocalDate;

import com.sda.carrental.model.User;


public class Return
{
    User employee;
    LocalDate dateTo;
    Reservation reservation;
    int supplement;
    String remarks;
}
