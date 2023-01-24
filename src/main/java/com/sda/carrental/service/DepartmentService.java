package com.sda.carrental.service;


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

    public List<Department> findAllWhereCountry(Department.CountryCode countryCode) {
        return StreamSupport.stream(departmentRepository.findDepartmentsByCountryCode(countryCode).spliterator(), false)      //nowa klasa StreamSupport
                .collect(toList());
    }

    public Department findAllWhereCountryCodeAndHq(Department.CountryCode countryCode) {
        return departmentRepository.findDepartmentsByCountryCodeAndHq(countryCode, true).orElse(new Department(Department.CountryCode.COUNTRY_EMPTY, "CityPlaceholder", "AddressPlaceholder", "PostcodePlaceholder", "email@email.com", "123456789", true));
    }

    public Department findDepartmentByDepartmentId(Long id){
        return departmentRepository.findDepartmentByDepartmentId(id);
    };

}
