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

    public Employee(final Department department, final Titles title)
    {
        this.department = department;
        this.title = title;
    }

    public Employee(final String email, final String password, final Roles role, final Department department, final Titles title)
    {
        super(email, password, role);
        this.department = department;
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private Titles title;

    public enum Titles {
        RANK_CLERK(1), RANK_MANAGER(2);

        Titles(int value) {
        }
    }
}
