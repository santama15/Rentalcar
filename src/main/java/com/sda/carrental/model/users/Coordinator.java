package com.sda.carrental.model.users;

import com.sda.carrental.model.property.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "coordinator")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "coordinator_id"))

public class Coordinator extends User {
    public Coordinator(String email, String password, String name, String surname, List<Department> departments, LocalDate terminationDate) {
        super(email, password, Roles.ROLE_COORDINATOR, name, surname);
        this.departments = departments;
        this.creationDate = LocalDate.now();
        this.terminationDate = terminationDate;
    }

    @OneToMany
    @Column(name = "departments")
    private List<Department> departments;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

}
