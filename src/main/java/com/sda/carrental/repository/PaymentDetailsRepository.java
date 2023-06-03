package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.PaymentDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, Long> {
    Optional<PaymentDetails> findByReservation(Reservation reservation);
}