package com.sda.carrental.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sda.carrental.model.car.Car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Getter
@NoArgsConstructor
@ToString
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String city;
    String street;
    User employee;
    Car car;
}
