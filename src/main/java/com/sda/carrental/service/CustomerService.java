package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.repository.CustomerRepository;
import com.sda.carrental.repository.ReservationRepository;
import com.sda.carrental.repository.UserRepository;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.ChangeAddressForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final VerificationService verificationService;
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

    public HttpStatus changeContact(String inputContact) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer user = findByUsername(cud.getUsername());
            user.setContactNumber(inputContact);
            repository.save(user);
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus changeAddress(ChangeAddressForm form) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer user = findByUsername(cud.getUsername());

            user.setCountry(form.getCountry());
            user.setCity(form.getCity());
            user.setAddress(form.getAddress());
            repository.save(user);
            return HttpStatus.ACCEPTED;
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String generateRandomString() {
        final int length = 30;
        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+`~";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    private Customer scrambleCustomer(Customer user) {
        user.setPassword(generateRandomString());
        user.setName(generateRandomString());
        user.setSurname(generateRandomString());
        user.setAddress(generateRandomString());
        user.setContactNumber(generateRandomString());
        do {
            user.setEmail(generateRandomString());
        } while (repository.findByEmail(user.getEmail()).isPresent());

        return user;
    }

    public HttpStatus deleteCustomer() {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer user = findByUsername(cud.getUsername());
            HttpStatus status = verificationService.deleteByCustomerId(user);

            if (status.equals(HttpStatus.OK)) {
                if (reservationRepository.findAllByUser(user).isEmpty()) {
                    repository.delete(user);
                } else {
                    repository.save(scrambleCustomer(user));
                }
                return HttpStatus.ACCEPTED;
            } else {
                return status;
            }
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            return HttpStatus.NOT_FOUND;
        } catch (Error err) {
            err.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
