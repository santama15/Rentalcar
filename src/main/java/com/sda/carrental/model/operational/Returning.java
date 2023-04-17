package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.property.PaymentDetails;
import com.sda.carrental.model.users.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "returning")
@Getter
@NoArgsConstructor
public class Returning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id", nullable = false)
    private Long returnId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "actual_date_end")
    private LocalDate actualDateTo;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "surcharge_payment_id", referencedColumnName = "payment_id")
    private PaymentDetails surcharge;

    @Column(name = "notes")
    private String notes;
}
