package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    public Reservation(Customer customer, Car car, Department departmentTake, Department departmentBack, LocalDate dateFrom, LocalDate dateTo, LocalDate dateCreated) {
        this.customer = customer;
        this.car = car;
        this.departmentTake = departmentTake;
        this.departmentBack = departmentBack;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateCreated = dateCreated;
        this.status = ReservationStatus.STATUS_PENDING;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;

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

    @Column(name = "status")
    private ReservationStatus status;

    @Getter
    public enum ReservationStatus {
        STATUS_COMPLETED("Zakończone"),
        STATUS_PROGRESS("W trakcie"),
        STATUS_PENDING("Oczekujące"),
        STATUS_RESERVED("Rezerwacja"),
        STATUS_CANCELED("Anulowane"),
        STATUS_REFUNDED("Zwrócone");

        final String text;
        ReservationStatus(String text) {
            this.text = text;
        }
    }
}


