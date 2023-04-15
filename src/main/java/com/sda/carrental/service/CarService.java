package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.repository.CarRepository;
import com.sda.carrental.web.mvc.form.CarFilterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    public Car findAvailableCar(LocalDate dateFrom, LocalDate dateTo, Long department, long carId) {
        return carRepository.findCarByCarIdAndAvailability(dateFrom, dateTo, department, carId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Car> filterCars(CarFilterForm filterForm) {
        ArrayList<Car> filteredCars = (ArrayList<Car>) carRepository.findAvailableCarsInDepartment(
                filterForm.getIndexData().getDateFrom(),
                filterForm.getIndexData().getDateTo(),
                filterForm.getIndexData().getDepartmentIdFrom());

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

    public Map<String, Object> getFilterProperties(List<Car> carList) {
        Set<String> brands = new HashSet<>();
        Set<Car.CarType> types = new HashSet<>();
        Set<Integer> seats = new HashSet<>();

        for (Car car : carList) {
            brands.add(car.getBrand());
            types.add(car.getCarType());
            seats.add(car.getSeats());
        }

        List<String> sortedBrands = new ArrayList<>(brands);
        sortedBrands.sort(null);
        List<Car.CarType> sortedTypes = new ArrayList<>(types);
        sortedTypes.sort(null);
        List<Integer> sortedSeats = new ArrayList<>(seats);
        sortedSeats.sort(null);

        Map<String, Object> carProperties = new HashMap<>();
        carProperties.put("brand", sortedBrands);
        carProperties.put("type", sortedTypes);
        carProperties.put("seats", sortedSeats);
        return carProperties;
    }
}