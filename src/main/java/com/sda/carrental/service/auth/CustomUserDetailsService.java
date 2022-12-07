package com.sda.carrental.service.auth;

import com.sda.carrental.service.auth.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sda.carrental.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        return repository.findByEmail(username)
            .map(CustomUserDetails::new)
            .orElseThrow(() -> new RuntimeException("User with login:" + username + " not found in database!"));
    }
}
