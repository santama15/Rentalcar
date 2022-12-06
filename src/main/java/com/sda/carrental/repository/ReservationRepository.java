package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
