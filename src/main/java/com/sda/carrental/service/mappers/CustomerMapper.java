package com.sda.carrental.service.mappers;


import com.sda.carrental.model.users.Customer;
import com.sda.carrental.web.mvc.form.CreateCustomerForm;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerForm form) {
        return new Customer(form.getEmail(), form.getPassword(), form.getName(), form.getSurname(), form.getAddress());
    }

    public static Customer userUpdate(Customer customer, CreateCustomerForm form) {
        customer.setName(form.getName());
        customer.setSurname(form.getSurname());
        customer.setAddress(form.getAddress());
        customer.setEmail(form.getEmail());
        customer.setPassword(form.getPassword());

        return customer;
    }
}
