package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "customer_id"))
public class Customer extends User
{

    public Customer(String email, String password, String name, String surname, String country, String address/*, String personalId, String driverId*/)
    {
        super(email, password, Roles.ROLE_CUSTOMER, name, surname);
        this.country = country;
        this.address = address;
    }

    @Column(name = "country") //everything below should be encrypted
    private String country;

    @Column(name = "address")
    private String address;
/*
    @Column(name = "personal_id")
    private String personalId;

    @Column(name = "driver_id")
    private String driverId;*/ //TODO probably add another table for company invoices + move invoice creation to payment step (needs consideration) + change languages used to single one
}
