package com.sda.carrental.model.property;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "department")
@Getter
@NoArgsConstructor
@ToString
public class Department {
    public Department(String city, String address) {
        this.city = city;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id", nullable = false)
    private Long branch_id;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;
}
