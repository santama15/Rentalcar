package com.sda.carrental.test;

import com.sda.carrental.model.Company;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Employee;
import com.sda.carrental.repository.CompanyRepository;
import com.sda.carrental.repository.DepartmentRepository;
import com.sda.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PredefiniedData implements CommandLineRunner {
    private final UserRepository uRepository;
    private final CompanyRepository cRepository;
    private final DepartmentRepository dRepository;

    @Override
    public void run(String... args) throws Exception {
        createCompany();
        createDepartments();

        createUsers();
    }

    private void createUsers() {
        uRepository.save(new Customer("user1@gmail.com", "silnehaslo123", "Imie", "Nazwisko", "ul. Ulica 123"));
        uRepository.save(new Customer("user2@gmail.com", "silnehaslo12", "Jakub", "Kowalski", "ul. Ulica 12"));
        uRepository.save(new Customer("user3@gmail.com", "silnehaslo", "Maciek", "Masło", "ul. Ulica 1"));
        uRepository.save(new Customer("user4@gmail.com", "silnehaslo1234", "Jan", "Orzech", "ul. Ulica 124"));
        uRepository.save(new Customer("user5@gmail.com", "slabehaslo321", "Katarzyna", "Kasztan", "ul. Ulica 133"));



        uRepository.save(new Employee("manager@gmail.com", "manager", "Maria", "Fajna", dRepository.findById(1L).orElse(null), Employee.Titles.RANK_MANAGER));
        uRepository.save(new Employee("pracownik1@gmail.com", "pracownik1", "Anna", "Mniejfajna", dRepository.findById(2L).orElse(null), Employee.Titles.RANK_CLERK));
        uRepository.save(new Employee("pracownik2@gmail.com", "pracownik2", "Karolina", "Nijaka", dRepository.findById(3L).orElse(null), Employee.Titles.RANK_CLERK));
    }

    private void createCompany() {
        cRepository.save(new Company("RentalCar", "www.rental-car.pl", "rental-car@gmail.com", "groupSIX", "RentalCar"));
    }

    private void createDepartments() {
        dRepository.save(new Department("Katowice", "ul. Fajna 1"));
        dRepository.save(new Department("Łódź", "ul. Niefajna 2"));
        dRepository.save(new Department("Gdańsk", "ul. Średnia 3"));
    }
}