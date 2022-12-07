package com.sda.carrental.repository;

import com.sda.carrental.model.property.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAll();

}
