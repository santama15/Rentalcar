package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Verification;
import com.sda.carrental.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository repository;

    public HttpStatus deleteByCustomerId(Customer customer) {
        try {
            repository.delete(repository.findByCustomer(customer).orElseThrow(ResourceNotFoundException::new));
            return HttpStatus.OK;
        } catch (ResourceNotFoundException err) {
            return HttpStatus.OK;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public Optional<Verification> getOptionalVerificationByCustomer(Customer customer) {
        return repository.findByCustomer(customer);
    }

    public Verification maskVerification(Verification verification) {
        return new Verification(verification.getCustomer(), verification.getPersonalId().replaceAll("^...", "XXX"), verification.getDriverId().replaceAll("...$", "XXX"));
    }
}
