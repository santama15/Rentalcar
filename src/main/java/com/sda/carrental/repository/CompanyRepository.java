package com.sda.carrental.repository;

import com.sda.carrental.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
