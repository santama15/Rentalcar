package com.sda.carrental.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity(name = "customer")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "customer_id"))
public class Customer extends User
{

    public Customer(String email, String password, String name, String surname, String address)
    {
        super(email, password, Roles.ROLE_CUSTOMER, name, surname);
        this.address = address;
    }
    @Setter
    @Column(name = "address")
    private String address;
}
