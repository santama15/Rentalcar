package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();

    @Query(value = "SELECT * from Reservation where customer_id  = :dCustomerId", nativeQuery = true)
    List<Reservation> findReservationByCustomerId(@Param("dCustomerId") Long dCustomerId);

    @Query(value = "DELETE FROM reservation WHERE reservation_id = :id", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteReservationByReservation_id(@Param("id") Long id);

}
