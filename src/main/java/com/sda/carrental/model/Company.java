package com.sda.carrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "company")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "website", nullable = false)
    String website;

    @Column(name = "owner", nullable = false)
    String owner;

    @Column(name = "logotype", nullable = false)
    String logotype;
}

