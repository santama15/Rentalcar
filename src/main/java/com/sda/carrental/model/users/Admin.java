package com.sda.carrental.model.users;

import com.sda.carrental.model.property.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "admin")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "admin_id"))

public class Admin extends User {
    public Admin(String email, String password, String name, String surname) {
        super(email, password, Roles.ROLE_ADMIN, name, surname);
    }
}
