package com.sda.carrental.model.car;

public class Car {
    String brand;
    String model;
    String bodyType;
    int year;
    String color;
    int Odometer;
    Status status;
    int price;

    public enum Status
    {
        BORROWED, AVAILABLE, UNAVAILABLE
    }
}
