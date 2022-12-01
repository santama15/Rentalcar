package com.sda.carrental.model.car;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CarRepository extends CrudRepository<Car, Long>
{
    List<Car> findAllByStatus(Enum status);
}
