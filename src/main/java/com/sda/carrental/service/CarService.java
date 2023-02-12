package com.sda.carrental.service;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    public Car findCarById(long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> findAvailableCarsInDepartment(LocalDate dateFrom, LocalDate dateTo, Long department){
        return StreamSupport.stream(carRepository.findAvailableCarsInDepartment(dateFrom, dateTo, department).spliterator(), false).collect(Collectors.toList());
    }
}