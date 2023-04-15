package com.sda.carrental.repository;

import com.sda.carrental.model.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    @Query(value = "select u.password from user u where u.email = :email")
    Optional<String> getPasswordByUsername(@Param(value = "email") String email);
}
