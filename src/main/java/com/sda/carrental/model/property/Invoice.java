package com.sda.carrental.model.property;

import com.sda.carrental.model.operational.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "invoice")
@Getter
@NoArgsConstructor
public class Invoice {
    public Invoice(Long value, Reservation reservation_id, boolean isPaid, boolean isRefunded, LocalDate payment_deadline) {
        this.value = value;
        this.reservation_id = reservation_id;
        this.isPaid = isPaid;
        this.isRefunded = isRefunded;
        this.payment_deadline = payment_deadline;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private Long invoice_id;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation_id;

    @Column(name = "value", nullable = false)
    private Long value;

    @Column(name = "paid", nullable = false)
    private boolean isPaid;

    @Column(name = "refunded", nullable = false)
    private boolean isRefunded;

    @Column(name = "deadline", nullable = false)
    private LocalDate payment_deadline;
}
