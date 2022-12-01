package com.sda.carrental.model.operating.rental;

import java.time.LocalDate;

import com.sda.carrental.model.User;
import com.sda.carrental.model.operating.reservation.Reservation;


public class Rental
{
    User employee;
    LocalDate dateFrom;
    Reservation reservation;
    String remarks;
}
