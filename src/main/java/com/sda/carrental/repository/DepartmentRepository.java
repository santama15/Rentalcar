package com.sda.carrental.repository;

import com.sda.carrental.model.property.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
