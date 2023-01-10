package com.sda.carrental.repository;

import java.time.LocalDate;
import java.util.List;

import com.sda.carrental.model.property.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAllByCarStatus(Car.CarStatus carStatus);
    List<Car> findAll();

    @Query(value = "from car where car_id in (select c.car_id " +
            "from car c " +
            "left join reservation r on c.car_id = r.car_id.car_id " +
            "where (r.reservation_id is null or r.dateTo < :ddateFrom or r.dateFrom> :ddateTo) and c.department_id.branch_id = :ddepartment " +
            "group by c.car_id)")

        //TODO Car/Repository
    List<Car> findUnreservedByDateAndDepartment(@Param("ddateFrom") LocalDate ddateFrom, @Param("ddateTo") LocalDate ddateTo, @Param("ddepartment") Long ddepartment);

}
