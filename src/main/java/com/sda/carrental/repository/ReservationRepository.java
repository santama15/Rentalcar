package com.sda.carrental.repository;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();

    @Query(value = "select c.car_id, c.brand, c.carStatus, c.carType, c.color, c.jpgLink, c.mileage, c.model, c.price_day, c.year, c.department_id " +
            "from car c " +
            "left join reservation r on c.car_id = r.car_id " +
            "where (r.reservation_id is null or r.dateTo < :ddateFrom or r.dateFrom> :ddateTo) and c.department_id = :ddepartment " +
            "group by c.car_id")

    //TODO Car/Repository
    List<Car> findUnreservedByDateAndDepartment(@Param("ddateFrom") LocalDate ddateFrom, @Param("ddateTo") LocalDate ddateTo, @Param("ddepartment") Long ddepartment);
}
