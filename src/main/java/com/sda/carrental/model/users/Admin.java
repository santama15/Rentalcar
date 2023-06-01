package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "admin")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "admin_id"))

public class Admin extends User {
    public Admin(String email, String password, String name, String surname) {
        super(email, password, Roles.ROLE_ADMIN, name, surname);
    }
}
