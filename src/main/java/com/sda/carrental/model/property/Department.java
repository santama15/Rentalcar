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

    public Department(CountryCode countryCode, String city, String address, String postcode, String email, String contact, boolean hq) {
        this.countryCode = countryCode;
        this.city = city;
        this.address = address;
        this.postcode = postcode;
        this.email= email;
        this.contact = contact;
        this.hq = hq;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "country_code", nullable = false)
    private CountryCode countryCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "hq", nullable = false)
    private boolean hq;

    @Getter
    public enum CountryCode {
        COUNTRY_EMPTY("EMPTY", "-00"), COUNTRY_PL("PL", "+48"), COUNTRY_GB("GB", "+44"), COUNTRY_NL("NL", "+31"), COUNTRY_DE("DE", "+49");

        final String code;
        final String contactCode;
        CountryCode(String code, String contactCode) {
            this.code = code;
            this.contactCode = contactCode;
        }
    }
}
