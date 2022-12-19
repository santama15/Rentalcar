package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "customer")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "customer_id"))
public class Customer extends User {

    public Customer(String email, String password, String name, String surname, String address)
    {
        super(email, password, Roles.ROLE_CUSTOMER, name, surname);
        this.address = address;
    }

    @Column(name = "address")
    private String address;
}
