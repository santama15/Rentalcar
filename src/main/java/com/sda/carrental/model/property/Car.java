package com.sda.carrental.model.property;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

@Entity(name = "car")
@Getter
@NoArgsConstructor
public class Car {
    public Car(Department department, String jpgLink, String brand, String model, Integer year, Long mileage, Integer seats, Integer price_day, CarType carType, CarStatus carStatus) {
        this.department_id = department;
        this.jpgLink = jpgLink;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.seats = seats;
        this.price_day = price_day;
        this.carType = carType;
        this.carStatus = carStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Long car_id;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    Department department_id;

    @Column(name = "jpg_link")
    String jpgLink;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Column(name = "year")
    Integer year;

    @Column(name = "mileage")
    Long mileage;

    @Column(name = "seats")
    Integer seats;

    @Column(name = "price_day")
    Integer price_day;

    @Column(name = "type")
    CarType carType;

    @Column(name = "status")
    CarStatus carStatus;


@Getter
    public enum CarType {
        TYPE_SEDAN("Sedan"), TYPE_SUV("SUV"), TYPE_COMPACT("Compact"), TYPE_WAGON("Kombi"), TYPE_COUPE("Coupe"), TYPE_VAN("Van"), TYPE_HATCHBACK("Hatchback"), TYPE_PICKUP("Pickup"), TYPE_SPORT("Sport");

        final String name;
        CarType(String name) {
            this.name = name;
        }
    }


    public enum CarStatus {
        STATUS_OPEN, STATUS_RENTED, STATUS_UNAVAILABLE
    }


    @Override
    public String toString() {
        return "Car{" +
                "seats=" + seats +
                '}';
    }
}
