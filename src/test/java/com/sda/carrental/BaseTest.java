package com.sda.carrental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.repository.DepartmentRepository;
import com.sda.carrental.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class BaseTest
{

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected BCryptPasswordEncoder encoder;

    @BeforeEach
    void beforeEach()
    {
        userRepository.save(new Customer("user6@gmail.com", encoder.encode("password1"), "Igor", "Kasztan", "ul. Ulica 137"));
        userRepository.save(new Customer("user7@gmail.com", encoder.encode("password1"), "Anna", "Kowalska", "ul. Ulica 138"));
        userRepository.save(new Customer("a@a", encoder.encode("a"), "Andrzej", "Nowak", "ul. Ulica 139"));

        departmentRepository.save(new Department(Department.CountryCode.COUNTRY_PL, "Katowice", "ul. Fajna 1", "40-000", "car-rental-alpha@gmail.com", "500 500 501", false));
        departmentRepository.save(new Department(Department.CountryCode.COUNTRY_PL, "Łódź", "ul. Niefajna 2", "90-000", "car-rental-beta@gmail.com", "500 500 502", false));
        departmentRepository.save(new Department(Department.CountryCode.COUNTRY_PL, "Gdańsk", "ul. Średnia 3", "80-000", "car-rental-gamma@gmail.com", "500 500 503", false));
        }

    @AfterEach
    void afterEach()
    {
        userRepository.deleteAll();
        departmentRepository.deleteAll();
    }
}
