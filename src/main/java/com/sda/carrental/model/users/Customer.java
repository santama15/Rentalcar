package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "customer")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "customer_id"))
public class Customer extends User {

    public Customer(String name, String surname, String email, String password, Roles role, String address)
    {
        super(email, password, role, name, surname);
        this.address = address;
    }

    @Column(name = "address")
    private String address;
}
