package com.sda.carrental.repository;

import java.time.LocalDate;
import java.util.List;

import com.sda.carrental.model.property.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends CrudRepository<Car, Long> {
    @Query(value = "from car where car_id in (" +
            "select c.car_id from car c left join reservation r on c.car_id = r.car_id.car_id " +
            "where (r.reservation_id is null or r.dateTo < :dateFrom or r.dateFrom> :dateTo) " +
            "and c.department_id.branch_id = :department " +
            "and c.carStatus != 2 " +
            "group by c.car_id)")

    List<Car> findAvailableCarsInDepartment(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("department") Long department);
}
