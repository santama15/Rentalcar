package com.sda.carrental.service;


import com.sda.carrental.model.property.Department;
import com.sda.carrental.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)      //nowa klasa StreamSupport
                .collect(toList());
    }


}
