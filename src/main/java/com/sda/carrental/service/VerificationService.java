package com.sda.carrental.service;

import com.sda.carrental.model.users.Customer;
import com.sda.carrental.repository.VerificationRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final UserService userService;

    public boolean isVerified(CustomUserDetails cud) {
            return verificationRepository
                    .findByCustomer((Customer) userService.findByUsername(cud.getUsername()))
                    .isPresent();
    }
}
