package com.sda.carrental.model.property;

import com.sda.carrental.model.operational.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "reservation_payment")
@Getter
@NoArgsConstructor
public class ReservationPayment {
    public ReservationPayment(Double mainValue, Double depositValue, Reservation reservation) {
        this.mainValue = mainValue;
        this.deposit = depositValue;
        this.reservation = reservation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    @Setter
    @Column(name = "main_value", nullable = false)
    private double mainValue;

    @Column(name = "deposit", nullable = false)
    private double deposit;
}
