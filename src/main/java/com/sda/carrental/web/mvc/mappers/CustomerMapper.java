package com.sda.carrental.web.mvc.mappers;


import com.sda.carrental.model.users.User;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.web.mvc.form.CreateCustomerForm;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerForm form) {
        return new Customer(form.getName(), form.getSurname(), form.getEmail(), form.getPassword(), form.getAddress());
    }

    public static Customer userUpdate(Customer customer, CreateCustomerForm form) {
        customer.setName(form.getName());
        customer.setSurname(form.getSurname());
        customer.setEmail(form.getEmail());
        customer.setPassword(form.getPassword());
        customer.setAddress(form.getAddress());

        return customer;
    }
}
