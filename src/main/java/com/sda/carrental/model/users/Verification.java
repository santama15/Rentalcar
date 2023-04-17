package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "verification")
@Getter
@Setter
@NoArgsConstructor
public class Verification {

    public Verification(Customer customer, String personalId, String driverId) {  //TODO should be encrypted
        this.customer = customer;
        this.personalId = personalId;
        this.driverId = driverId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id", nullable = false)
    private Long verificationId;


    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "personal_id")
    private String personalId;

    @Column(name = "driver_id")
    private String driverId;
}
