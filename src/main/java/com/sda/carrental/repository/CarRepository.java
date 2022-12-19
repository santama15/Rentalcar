package com.sda.carrental.repository;

import java.util.List;

import com.sda.carrental.model.property.Car;
import org.springframework.data.repository.CrudRepository;


public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAllByCarStatus(Car.CarStatus carStatus);
}
