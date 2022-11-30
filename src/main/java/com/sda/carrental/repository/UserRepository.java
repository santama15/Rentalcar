package com.sda.carrental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sda.carrental.model.User;


public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByLogin(String login);
}
