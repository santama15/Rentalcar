package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.repository.CarRepository;
import com.sda.carrental.web.mvc.form.CarFilterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
    }

    public List<Car> findAvailableCarsInDepartment(LocalDate dateFrom, LocalDate dateTo, Long department) {
        return carRepository.findAvailableCarsInDepartment(dateFrom, dateTo, department);
    }

    public List<Car> filterCars(CarFilterForm filterForm) {
        ArrayList<Car> filteredCars = (ArrayList<Car>) carRepository.findAvailableCarsInDepartment(
                filterForm.getIndexData().getDateFrom(),
                filterForm.getIndexData().getDateTo(),
                filterForm.getIndexData().getBranch_id_from());

        if (filterForm.getPriceMin() != null) {
            filteredCars.removeIf(car -> car.getPrice_day() < filterForm.getPriceMin());
        }

        if (filterForm.getPriceMax() != null) {
            filteredCars.removeIf(car -> car.getPrice_day() > filterForm.getPriceMax());
        }

        if (filterForm.getBrands() != null) {
            filteredCars.removeIf(car -> !filterForm.getBrands().contains(car.getBrand()));
        }

        if (filterForm.getSeats() != null) {
            filteredCars.removeIf(car -> !filterForm.getSeats().contains(car.getSeats()));
        }

        if (filterForm.getTypes() != null) {
            filteredCars.removeIf(car -> !filterForm.getTypes().contains(car.getCarType()));
        }

        return filteredCars;
    }
}