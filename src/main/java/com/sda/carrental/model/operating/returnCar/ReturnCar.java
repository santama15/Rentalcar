package com.sda.carrental.model.operating.returnCar;

import java.time.LocalDate;

import com.sda.carrental.model.User;
import com.sda.carrental.model.operating.reservation.Reservation;


public class ReturnCar
{
    User employee;
    LocalDate dateTo;
    Reservation reservation;
    int supplement;
    String remarks;
}
