package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "client")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "client_id"))
public class Client extends User {

    public Client(final String address)
    {
        this.address = address;
    }

    public Client(final String email, final String password, final Roles role, final String address)
    {
        super(email, password, role);
        this.address = address;
    }

    @Column(name = "address")
    private String address;
}
