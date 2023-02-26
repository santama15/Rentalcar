package com.sda.carrental.service;


import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.User;
import com.sda.carrental.repository.CarRepository;
import com.sda.carrental.repository.DepartmentRepository;
import com.sda.carrental.repository.ReservationRepository;
import com.sda.carrental.repository.UserRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.IndexForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final DepartmentRepository depRepository;

    public HttpStatus createReservation(@RequestBody CustomUserDetails cud, @RequestBody Long carId, @RequestBody IndexForm indexForm) {
        try {
            //TODO create proper mapper layer
            User user = userRepository.findByEmail(cud.getUsername()).orElseThrow(ResourceNotFoundException::new);
            List<Car> avCars = carRepository.findAvailableCarsInDepartment(indexForm.getDateFrom(), indexForm.getDateTo(), indexForm.getBranch_id_from());
            Car reqCar = carRepository.findById(carId).orElseThrow(ResourceNotFoundException::new);
            Department depRepFrom = depRepository.findById(indexForm.getBranch_id_from()).orElseThrow(ResourceNotFoundException::new);
            Department depRepTo = depRepository.findById(indexForm.getBranch_id_to()).orElseThrow(ResourceNotFoundException::new);

            if (avCars.contains(reqCar) && !indexForm.getDateFrom().isAfter(indexForm.getDateTo()) && !LocalDate.now().isAfter(indexForm.getDateFrom())) {
                reservationRepository.save(
                        new Reservation(
                                (Customer) user, reqCar,
                                depRepFrom, depRepTo,
                                indexForm.getDateFrom(), indexForm.getDateTo(),
                                indexForm.getDateCreated()));
                return HttpStatus.CREATED;
            } else {
                return HttpStatus.CONFLICT;
            }
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
