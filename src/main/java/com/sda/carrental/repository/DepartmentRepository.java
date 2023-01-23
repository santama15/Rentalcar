package com.sda.carrental.repository;

import com.sda.carrental.model.property.Department;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAll();

    List<Department> findDepartmentsByCountryCode(Department.CountryCode countryCode);

    Optional<Department> findDepartmentsByCountryCodeAndHq(Department.CountryCode countryCode, boolean hq);
    @Query(value = "SELECT * from Department where department_id = :did", nativeQuery = true)
    Department findDepartmentByDepartmentId(Long did);

}
