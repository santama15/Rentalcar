package com.sda.carrental.model.operating;

import java.time.LocalDate;
import java.util.List;

import com.sda.carrental.model.User;


public class Rental
{
    User employee;
    LocalDate dateFrom;
    Reservation reservation;
    String remarks;
}
