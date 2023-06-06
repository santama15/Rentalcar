package com.sda.carrental.service;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.users.Manager;
import com.sda.carrental.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository repository;

    public Manager findManagerByUsername(String username) {
        return repository.findManagerByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }
}
