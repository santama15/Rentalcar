package com.sda.carrental.model.users;

import com.sda.carrental.model.property.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "employee")
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "employee_id"))

public class Employee extends User {
    public Employee(String email, String password, String name, String surname, Department department, Ranks rank) {
        super(email, password, Roles.ROLE_EMPLOYEE);
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.rank = rank;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank")
    private Ranks rank;

    public enum Ranks {
        RANK_CLERK(1), RANK_MANAGER(2);

        Ranks(int value) {
        }
    }
}
