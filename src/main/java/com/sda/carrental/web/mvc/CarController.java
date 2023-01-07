package com.sda.carrental.web.mvc;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping

public class CarController {

    private final CarService carService;
    private final ReservationService reservationService;

/*    @GetMapping("/show")
    public String showResults(final ModelMap map) {
        List<Car> cars = carService.findAll();
        map.addAttribute("cars", cars);
        return "showResults";
    }*/

    @GetMapping("/show")
    public String showResults(final ModelMap map) {
        List<Car> carList = reservationService.findUnreservedCars(LocalDate.now(),LocalDate.now().plusDays(2), 2L);
        map.addAttribute("cars", carList);
        return "showResults";
    }




}