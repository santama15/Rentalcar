package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "customer")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "customer_id"))
public class Customer extends User {
    public Customer(String email, String password, Roles role, String name, String surname, String address) {
        super(email, password, role);
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;
}
