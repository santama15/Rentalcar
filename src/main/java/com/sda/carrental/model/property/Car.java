package com.sda.carrental.model.property;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

@Entity(name = "car")
@Getter
@NoArgsConstructor
public class Car {
    public Car(String brand, String model, Integer year, Long mileage, Color color, Integer price_day, CarType carType, CarStatus carStatus) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.price_day = price_day;
        this.carType = carType;
        this.carStatus = carStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id", nullable = false)
    private Long car_id;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    Department department_id;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Column(name = "year")
    Integer year;

    @Column(name = "mileage")
    Long mileage;

    @Column(name = "color")
    Color color;

    @Column(name = "price_day")
    Integer price_day;

    @Column(name = "type")
    CarType carType;

    @Column(name = "status")
    CarStatus carStatus;

    public enum CarType {
        TYPE_SEDAN, TYPE_SUV, TYPE_COMPACT, TYPE_WAGON, TYPE_COUPE, TYPE_VAN, TYPE_HATCHBACK, TYPE_PICKUP, TYPE_SPORT
    }

    public enum CarStatus {
        STATUS_OPEN, STATUS_RENTED, STATUS_UNAVAILABLE
    }
}
