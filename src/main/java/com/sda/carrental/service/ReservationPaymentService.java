package com.sda.carrental.service;

import com.sda.carrental.constants.GlobalValues;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.ReservationPayment;
import com.sda.carrental.repository.ReservationPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationPaymentService {
    private final ReservationPaymentRepository reservationPaymentRepository;
    private final GlobalValues gv;

    public List<ReservationPayment> findAll() {
        return reservationPaymentRepository.findAll();
    }

    public ReservationPayment findByReservation (Reservation reservation) {
        return reservationPaymentRepository
                .findByReservation(reservation)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void createReservationPayment(Reservation reservation) {
        long days = reservation.getDateFrom().until(reservation.getDateTo(), ChronoUnit.DAYS) + 1;

        double value = days * reservation.getCar().getPrice_day();
        if (!reservation.getDepartmentBack().equals(reservation.getDepartmentTake())) {
            value += gv.getDeptReturnPriceDiff();
        }

        reservationPaymentRepository.save(
                new ReservationPayment(value*(1-gv.getDepositPercentage()), value*gv.getDepositPercentage(), reservation)
        );
    }

    public void retractReservationPayment(Reservation reservation) {
        if (LocalDate.now().isAfter(reservation.getDateFrom().minusDays(gv.getRefundSubtractDaysDuration()))) {
            ReservationPayment rp = findByReservation(reservation);
            rp.setMainValue(0);
            reservationPaymentRepository.save(rp);
        } else {
            reservationPaymentRepository.deleteByReservation(reservation);
        }
    }
}
