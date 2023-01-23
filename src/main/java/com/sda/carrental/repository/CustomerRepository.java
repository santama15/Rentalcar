package com.sda.carrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.User;


public interface CustomerRepository extends CrudRepository<User, Long>
{
    @Query(value = "SELECT * from Customer where id = :did", nativeQuery = true)
    Customer findByCustomerId(Long did);

    @Query(value = "SELECT * from User where name  = :dCustomerName", nativeQuery = true)
    List<User> findAllByLastName(String dCustomerName);
}

//    @Query(value = "SELECT * from Reservation where customer_id  = :dCustomerId", nativeQuery = true)
//    List<Reservation> findReservationByCustomerId(@Param("dCustomerId") Long dCustomerId);