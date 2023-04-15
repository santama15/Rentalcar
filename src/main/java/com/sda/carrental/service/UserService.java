package com.sda.carrental.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.service.auth.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sda.carrental.model.users.User;
import com.sda.carrental.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));
    }

    public User findByUsername(String username) {
        return repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
    }

    public void save(User user) {
        user.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
        repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public HttpStatus changePassword(String inputPassword) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = findByUsername(cud.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(inputPassword));
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

    public HttpStatus changeEmail(String inputEmail) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = findByUsername(cud.getUsername());
            user.setEmail(inputEmail);
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

    public boolean isEmailUnique(String input) {
        return repository.findByEmail(input).isEmpty();
    }
}
