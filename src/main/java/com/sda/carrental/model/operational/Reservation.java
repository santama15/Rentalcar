package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation {
    public Reservation(Customer customer_id, Car car_id, Department departmentTake, Department departmentBack, LocalDate dateFrom, LocalDate dateTo, LocalDate dateCreated) {
        this.customer_id = customer_id;
        this.car_id = car_id;
        this.departmentTake = departmentTake;
        this.departmentBack = departmentBack;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateCreated = dateCreated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id", nullable = false)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer_id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car_id;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department departmentTake;

    @ManyToOne
    @JoinColumn(name = "department_return_id", referencedColumnName = "department_id")
    private Department departmentBack;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "date_created")
    private LocalDate dateCreated;
}
