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
    private final CarService carService;
    private final DepartmentService departmentService;

    private final ReservationPaymentService reservationPaymentService;

    public HttpStatus createReservation(@RequestBody CustomUserDetails cud, @RequestBody ShowCarsForm form) {
        try {
            IndexForm index = form.getIndexData();

            User user = userService.findByUsername(cud.getUsername());
            Car car = carService.findAvailableCar(index.getDateFrom(), index.getDateTo(), index.getDepartmentIdFrom(), form.getCarId());
            Department depRepFrom = departmentService.findBranchWhereId(index.getDepartmentIdFrom());
            Department depRepTo = departmentService.findBranchWhereId(index.getDepartmentIdTo());

            Reservation reservation = new Reservation(
                    (Customer) user, car,
                    depRepFrom, depRepTo,
                    index.getDateFrom(), index.getDateTo(),
                    index.getDateCreated());

            reservationRepository.save(reservation);

            return HttpStatus.CREATED;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public List<Reservation> getUserReservations(@RequestBody CustomUserDetails cud) {
        return reservationRepository
                .findAllByUser(userService.findByUsername(cud.getUsername()));
    }

    public Reservation getUserReservation(@RequestBody CustomUserDetails cud, Long reservation_id) {
        return reservationRepository
                .findByUserAndId(userService.findByUsername(cud.getUsername()), reservation_id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void updateReservationStatus(Reservation reservation, Reservation.ReservationStatus newStatus) {
        reservation.setStatus(newStatus);
        reservationRepository.save(reservation);
    }

    public HttpStatus setUserReservationStatus(@RequestBody CustomUserDetails cud, Long reservation_id, Reservation.ReservationStatus statusReserved) {
        try {
            Reservation r = getUserReservation(cud, reservation_id);

            switch (statusReserved) {
                case STATUS_REFUNDED:
                    if (r.getStatus().equals(Reservation.ReservationStatus.STATUS_RESERVED)) {
                        //refund money logic here
                        reservationPaymentService.retractReservationPayment(r);
                        updateReservationStatus(r, Reservation.ReservationStatus.STATUS_REFUNDED);
                        return HttpStatus.ACCEPTED;
                    }
                    break;
                case STATUS_RESERVED:
                    if (r.getStatus().equals(Reservation.ReservationStatus.STATUS_PENDING)) {
                        //payment logic here
                        updateReservationStatus(r, Reservation.ReservationStatus.STATUS_RESERVED);
                        reservationPaymentService.createReservationPayment(r);
                        return HttpStatus.ACCEPTED;
                    }
                    break;
                case STATUS_CANCELED:
                    if (r.getStatus().equals(Reservation.ReservationStatus.STATUS_PENDING)) {
                        updateReservationStatus(r, Reservation.ReservationStatus.STATUS_CANCELED);
                        return HttpStatus.ACCEPTED;
                    }
                    break;
            }
            return HttpStatus.BAD_REQUEST;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
    }
}
