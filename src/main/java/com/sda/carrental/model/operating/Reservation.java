package com.sda.carrental.model.operating;

import java.time.LocalDate;

import com.sda.carrental.model.Car;
import com.sda.carrental.model.Department;
import com.sda.carrental.model.User;


public class Reservation
{
    int idNumber;
    LocalDate dateReservation;
    User client;
    Car car;
    LocalDate dateFrom;
    LocalDate dateTo;
    Department departmentTake;
    Department departmentBack;
    int amount;
}
