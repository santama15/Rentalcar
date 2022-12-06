package com.sda.carrental.repository;

import com.sda.carrental.model.property.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
