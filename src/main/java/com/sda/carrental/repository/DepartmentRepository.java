package com.sda.carrental.repository;

import com.sda.carrental.model.property.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAll();

    List<Department> findDepartmentsByCountryCode(Department.CountryCode countryCode);

    Optional<Department> findDepartmentsByCountryCodeAndHq(Department.CountryCode countryCode, boolean hq);

}
