package com.sda.carrental.repository;

import com.sda.carrental.model.users.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query(value = "FROM employee e WHERE e.email=:username")
    Optional<Employee> findEmployeeByUsername(@Param("username") String username);
}
