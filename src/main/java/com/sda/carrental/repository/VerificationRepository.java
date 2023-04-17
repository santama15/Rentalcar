package com.sda.carrental.repository;

import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Verification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationRepository extends CrudRepository<Verification, Long> {
    Optional<Verification> findByCustomer(Customer customer);
    void delete(Verification verification);
}
