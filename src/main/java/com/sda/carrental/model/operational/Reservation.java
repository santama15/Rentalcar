package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.property.Invoice;
import com.sda.carrental.model.users.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation {
    public Reservation(Client client_id, Car car_id, Department departmentTake, Department departmentBack, LocalDate dateFrom, LocalDate dateTo, LocalDate dateCreated, Invoice invoice_id) {
        this.client_id = client_id;
        this.car_id = car_id;
        this.departmentTake = departmentTake;
        this.departmentBack = departmentBack;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateCreated = dateCreated;
        this.invoice_id = invoice_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id", nullable = false)
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client_id;

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

    @OneToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    private Invoice invoice_id;
}
