package com.sda.carrental.service;


import com.sda.carrental.constants.enums.Country;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    public List<Department> findAllWhereCountry(Country country) {
        return StreamSupport.stream(departmentRepository.findDepartmentsByCountry(country).spliterator(), false)
                .collect(toList());
    }

    public Department findAllWhereCountryAndHq(Country country) {
        return departmentRepository.findDepartmentByCountryAndHq(country, true).orElse(new Department(Country.COUNTRY_PL, "—", "—", "—", "—", "—", true));
    }

    public Department findBranchWhereId(long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }

}
