package com.sda.carrental.repository;

import com.sda.carrental.model.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}
