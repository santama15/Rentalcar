package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.property.Invoice;
import com.sda.carrental.model.users.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "returning")
@Getter
@NoArgsConstructor
public class Returning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "return_id", nullable = false)
    private Long return_id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "actual_date_end")
    private LocalDate actualDateTo;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "surcharge_invoice_id", referencedColumnName = "invoice_id")
    private Invoice surcharge;

    @Column(name = "notes")
    private String notes;
}
