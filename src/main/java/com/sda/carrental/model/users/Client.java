package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "client")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "client_id"))
public class Client extends User {
    public Client(String email, String password, String name, String surname, String address) {
        super(email, password, Roles.ROLE_CLIENT);
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
