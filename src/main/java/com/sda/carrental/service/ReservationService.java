package com.sda.carrental.service;


import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.User;
import com.sda.carrental.repository.ReservationRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.IndexForm;
import com.sda.carrental.web.mvc.form.ShowCarsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final CustomerService customerService;
    private final CarService carService;
    private final DepartmentService departmentService;

    private final PaymentDetailsService paymentDetailsService;

    public HttpStatus createReservation(@RequestBody CustomUserDetails cud, @RequestBody ShowCarsForm form) {
        try {
            IndexForm index = form.getIndexData();

            User user = userService.findByUsername(cud.getUsername());
            Car car = carService.findAvailableCar(index.getDateFrom(), index.getDateTo(), index.getDepartmentIdFrom(), form.getCarId());
            Department depRepFrom = departmentService.findDepartmentWhereId(index.getDepartmentIdFrom());
            Department depRepTo = departmentService.findDepartmentWhereId(index.getDepartmentIdTo());

            Reservation reservation = new Reservation(
                    (Customer) user, car,
                    depRepFrom, depRepTo,
                    index.getDateFrom(), index.getDateTo(),
                    index.getDateCreated());
            reservationRepository.save(reservation);

            //payment method here linked with methods below this comment vvv
            reservation.setStatus(Reservation.ReservationStatus.STATUS_RESERVED);
            reservationRepository.save(reservation);
            // ^^^

            paymentDetailsService.createReservationPayment(reservation);

            return HttpStatus.CREATED;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public List<Reservation> getUserReservations(@RequestBody String username) {
        return reservationRepository
                .findAllByUser(userService.findByUsername(username));
    }

    public Reservation getUserReservation(@RequestBody String username, Long reservation_id) {
        return reservationRepository
                .findByUserAndId(userService.findByUsername(username), reservation_id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void updateReservationStatus(Reservation reservation, Reservation.ReservationStatus newStatus) {
        reservation.setStatus(newStatus);
        reservationRepository.save(reservation);
    }

    public HttpStatus setUserReservationStatus(@RequestBody CustomUserDetails cud, Long reservationId, Reservation.ReservationStatus status) {
        try {
            Reservation r = getUserReservation(cud.getUsername(), reservationId);

            if (status.equals(Reservation.ReservationStatus.STATUS_REFUNDED) && r.getStatus().equals(Reservation.ReservationStatus.STATUS_RESERVED)) {
                paymentDetailsService.retractReservationPayment(r);
                updateReservationStatus(r, Reservation.ReservationStatus.STATUS_REFUNDED);
                return HttpStatus.ACCEPTED;
            }
            return HttpStatus.BAD_REQUEST;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
    }

    public List<Reservation> getUserReservationsByDepartmentTake(String username, Long departmentId) {
        return reservationRepository
                .findAllByUsernameAndDepartmentId(username, departmentId);
    }
}
