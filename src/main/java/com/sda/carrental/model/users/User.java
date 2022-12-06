package com.sda.carrental.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user")
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    public User(String email, String password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    public enum Roles {
        ROLE_CUSTOMER(1), ROLE_EMPLOYEE(2);

        Roles(int value) {
        }
    }
}
