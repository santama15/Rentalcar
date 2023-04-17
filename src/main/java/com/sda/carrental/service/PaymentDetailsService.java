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
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final GlobalValues gv;

    public List<PaymentDetails> findAll() {
        return paymentDetailsRepository.findAll();
    }

    public PaymentDetails findByReservation(Reservation reservation) {
        return paymentDetailsRepository
                .findByReservation(reservation)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public void createReservationPayment(Reservation reservation) {
        long days = reservation.getDateFrom().until(reservation.getDateTo(), ChronoUnit.DAYS) + 1;

        double value = days * reservation.getCar().getPrice_day();
        if (!reservation.getDepartmentBack().equals(reservation.getDepartmentTake())) {
            value += gv.getDeptReturnPriceDiff();
        }

        paymentDetailsRepository.save(
                new PaymentDetails(value, reservation.getCar().getDepositValue(), reservation)
        );
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
}
