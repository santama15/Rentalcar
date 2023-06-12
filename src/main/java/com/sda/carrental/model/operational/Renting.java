package com.sda.carrental.model.operational;

import java.time.LocalDate;

import com.sda.carrental.model.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "rent")
@Getter
@NoArgsConstructor
public class Renting {
    public Renting(User employee_id, Reservation reservation) {
        this.employee_id = employee_id;
        this.reservation = reservation;
        this.actualDateFrom = LocalDate.now();
        this.remarks = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id", nullable = false)
    private Long rent_id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private User employee_id;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id")
    private Reservation reservation;

    @Column(name = "actual_date_from")
    private LocalDate actualDateFrom;

    @Column(name = "notes")
    private String remarks;
}
