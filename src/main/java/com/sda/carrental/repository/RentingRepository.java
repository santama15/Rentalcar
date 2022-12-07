package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Renting;
import org.springframework.data.repository.CrudRepository;

public interface RentingRepository extends CrudRepository<Renting, Long> {
}
