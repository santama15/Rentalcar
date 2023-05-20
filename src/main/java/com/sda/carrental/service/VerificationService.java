package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.repository.VerificationRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository repository;
    private final UserService userService;

    public boolean isVerified(CustomUserDetails cud) {
        return repository
                .findByCustomer((Customer) userService.findByUsername(cud.getUsername()))
                .isPresent();
    }

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
}
