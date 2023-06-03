package com.sda.carrental.service;

import com.sda.carrental.constants.GlobalValues;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.PaymentDetails;
import com.sda.carrental.repository.PaymentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final GlobalValues gv;


    public PaymentDetails findByReservation(Reservation reservation) {
        return paymentDetailsRepository
                .findByReservation(reservation)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void createReservationPayment(Reservation reservation) {
        long days = reservation.getDateFrom().until(reservation.getDateTo(), ChronoUnit.DAYS) + 1;

        double rawValue = days * reservation.getCar().getPrice_day();
        double payment = rawValue;
        double depositValue = reservation.getCar().getDepositValue();
        if (!reservation.getDepartmentBack().equals(reservation.getDepartmentTake())) {
            payment += gv.getDeptReturnPriceDiff();
            paymentDetailsRepository.save(new PaymentDetails(rawValue, gv.getDeptReturnPriceDiff(), depositValue, payment, depositValue, reservation));
        } else {
            paymentDetailsRepository.save(new PaymentDetails(rawValue, 0.0, depositValue, payment, depositValue, reservation));
        }
    }

    public void retractReservationPayment(Reservation reservation) {
        PaymentDetails paymentDetails = findByReservation(reservation);
        if (LocalDate.now().isAfter(reservation.getDateFrom().minusDays(gv.getRefundSubtractDaysDuration()))) {
            paymentDetails.setSecuredValue(paymentDetails.getMainValue() * gv.getDepositPercentage());
        }

        //some method here that would return money to the customer

        paymentDetails.setMainValue(0);
        paymentDetails.setDeposit(0);
        paymentDetailsRepository.save(paymentDetails);
    }

    public boolean isReservationFined(Reservation reservation) {
        return paymentDetailsRepository.findByReservation(reservation).isPresent();
    }
}
