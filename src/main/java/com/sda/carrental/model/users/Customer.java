package com.sda.carrental.model.users;

import com.sda.carrental.constants.enums.Country;
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

    public Customer(String email, String password, String name, String surname, Country country, String city, String address, String contactNumber)
    {
        super(email, password, Roles.ROLE_CUSTOMER, name, surname);
        this.country = country;
        this.city = city;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    @Column(name = "country") //TODO everything below should be encrypted
    private Country country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;
}
