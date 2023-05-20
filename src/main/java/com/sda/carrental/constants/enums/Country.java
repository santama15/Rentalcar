package com.sda.carrental.constants.enums;

import lombok.Getter;

@Getter
public enum Country {
    COUNTRY_PL("Polska", "PL", "+48"), COUNTRY_GB("Wielka Brytania", "GB", "+44"), COUNTRY_NL("Holandia", "NL", "+31"), COUNTRY_DE("Niemcy", "DE", "+49");

    final String countryName;
    final String code;
    final String contactCode;
    Country(String countryName, String code, String contactCode) {
        this.countryName = countryName;
        this.code = code;
        this.contactCode = contactCode;
    }
}
