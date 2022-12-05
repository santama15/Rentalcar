package com.sda.carrental.model.property;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "invoice")
@Getter
@NoArgsConstructor
public class Invoice {
    public Invoice(Long value, boolean isPaid, boolean isRefunded, LocalDate payment_deadline) {
        this.value = value;
        this.isPaid = isPaid;
        this.isRefunded = isRefunded;
        this.payment_deadline = payment_deadline;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invoice_id", nullable = false)
    private Long invoice_id;

    @Column(name = "value", nullable = false)
    private Long value;

    @Column(name = "paid", nullable = false)
    private boolean isPaid;

    @Column(name = "refunded", nullable = false)
    private boolean isRefunded;

    @Column(name = "deadline", nullable = false)
    private LocalDate payment_deadline;
}
