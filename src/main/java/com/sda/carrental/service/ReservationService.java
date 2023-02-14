package com.sda.carrental.service;


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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final DepartmentRepository depRepository;

    public HttpStatus createReservation(@RequestBody CustomUserDetails cud, @RequestBody Long carId, @RequestBody IndexForm form) {
        Optional<User> user = userRepository.findByEmail(cud.getUsername());
        List<Car> avCars = carRepository.findAvailableCarsInDepartment(form.getDateFrom(), form.getDateTo(), form.getBranch_id_from());
        Optional<Car> reqCar = carRepository.findById(carId);
        Optional<Department> depRepFrom = depRepository.findById(form.getBranch_id_from());
        Optional<Department> depRepTo = depRepository.findById(form.getBranch_id_to());

        if (reqCar.isPresent() && user.isPresent() && depRepTo.isPresent() && depRepFrom.isPresent()) {
            if (avCars.contains(reqCar.get()) && !form.getDateFrom().isAfter(form.getDateTo()) && !LocalDate.now().isAfter(form.getDateFrom())) {
                reservationRepository.save(
                        new Reservation(
                                (Customer) user.get(), reqCar.get(),
                                depRepFrom.get(), depRepTo.get(),
                                form.getDateFrom(), form.getDateTo(),
                                form.getDateCreated()));
                return HttpStatus.CREATED;
            } else {
                return HttpStatus.CONFLICT;
            }
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
