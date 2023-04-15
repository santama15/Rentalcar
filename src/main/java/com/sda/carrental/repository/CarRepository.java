package com.sda.carrental.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.sda.carrental.model.property.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends CrudRepository<Car, Long> {

    @Query(value = "from car where carId in (" +
            "select c.carId from car c left join reservation r on c.carId = r.car.carId " +
            "where (r.reservationId is null or r.dateTo < :dateFrom or r.dateFrom> :dateTo) " +
            "and c.department.departmentId = :department " +
            "and c.carStatus <> 2 " +
            "group by c.carId)")
    List<Car> findAvailableCarsInDepartment(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("department") Long department);

    @Query(value = "from car where carId = :carId " +
            "and carId in (" +
            "select c.carId from car c " +
            "left join reservation r on c.carId = r.car.carId " +
            "where (r.reservationId is null or r.dateTo < :dateFrom or r.dateFrom> :dateTo) " +
            "and c.department.departmentId = :department " +
            "and c.carStatus <> 2 " +
            "group by c.carId)")
    Optional<Car> findCarByCarIdAndAvailability(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("department") Long department, @Param("carId") long carId);
}
