package com.sda.carrental.service;


import com.sda.carrental.model.property.Car;
import com.sda.carrental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Car> findUnreservedCars(LocalDate ddateFrom, LocalDate ddateTo, Long ddepartment){
        return StreamSupport.stream(reservationRepository.findUnreservedByDateAndDepartment(ddateFrom, ddateTo, ddepartment).spliterator(), false).collect(Collectors.toList());
    }
}
