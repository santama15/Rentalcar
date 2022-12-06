package com.sda.carrental.test;

import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Employee;
import com.sda.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PredefiniedData implements CommandLineRunner {
    private final UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        createArtificalUsers();
    }

    private void createArtificalUsers() {
        repository.save(new Customer("user1@gmail.com", "silnehaslo123", "Imie", "Nazwisko", "ul. Ulica 123"));
        repository.save(new Customer("user2@gmail.com", "silnehaslo12", "Jakub", "Kowalski", "ul. Ulica 12"));
        repository.save(new Customer("user3@gmail.com", "silnehaslo", "Maciek", "Masło", "ul. Ulica 1"));
        repository.save(new Customer("user4@gmail.com", "silnehaslo1234", "Jan", "Orzech", "ul. Ulica 124"));
        repository.save(new Customer("user5@gmail.com", "slabehaslo321", "Katarzyna", "Kasztan", "ul. Ulica 133"));

        repository.save(new Employee("manager@gmail.com", "manager", "Maria", "Fajna", null, Employee.Titles.RANK_MANAGER));
        repository.save(new Employee("pracownik@gmail.com", "pracownik", "Anna", "Mniejfajna", null, Employee.Titles.RANK_CLERK));
    }
}
