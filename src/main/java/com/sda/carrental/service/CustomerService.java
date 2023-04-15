package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.repository.CustomerRepository;
import com.sda.carrental.repository.UserRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Customer findByUsername(String username) {
        return repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
    }

    public boolean isCurrentPassword(CustomUserDetails cud, String inputPassword) {
        try {
            String storedPassword = userRepository.getPasswordByUsername(cud.getUsername()).orElseThrow(() -> new RuntimeException("User with username: " + cud.getUsername() + " not found"));
            return bCryptPasswordEncoder.matches(inputPassword, storedPassword);
        } catch (ResourceNotFoundException err) {
            return false;
        }
    }
}
