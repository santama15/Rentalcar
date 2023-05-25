package com.sda.carrental.model.users;

import com.sda.carrental.model.property.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "manager")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "manager_id"))

public class Manager extends User {
    public Manager(String email, String password, String name, String surname, Department department, LocalDate hireDate, LocalDate terminationDate) {
        super(email, password, Roles.ROLE_MANAGER, name, surname);
        this.department = department;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

}