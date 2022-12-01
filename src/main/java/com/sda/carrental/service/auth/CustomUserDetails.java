package com.sda.carrental.service.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import com.sda.carrental.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String getUsername()
    {
        return null;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }
}
