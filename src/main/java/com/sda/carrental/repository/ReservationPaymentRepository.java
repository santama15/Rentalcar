package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.ReservationPayment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationPaymentRepository extends CrudRepository<ReservationPayment, Long> {
    List<ReservationPayment> findAll();
    Optional<ReservationPayment> findByReservation(Reservation reservation);
    void deleteByReservation(Reservation reservation);
}