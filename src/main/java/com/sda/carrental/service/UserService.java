package com.sda.carrental.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sda.carrental.model.users.User;
import com.sda.carrental.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));
    }

    public User save(User user) {
        user.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
      user.setPassword((user.getPassword()));
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

//    @Override
//    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
//    {
//        final User user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
//        return new UserDetails()
//        {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities()
//            {
//                return List.of(new GrantedAuthority()
//                {
//                    @Override
//                    public String getAuthority()
//                    {
//                        return User.Roles.ROLE_EMPLOYEE.toString();
//                    }
//                });
//            }

//            @Override
//            public String getPassword()
//            {
//                return user.getPassword();
//            }
//
//            @Override
//            public String getUsername()
//            {
//                return user.getEmail();
//            }
//
//            @Override
//            public boolean isAccountNonExpired()
//            {
//                return true;
//            }
//
//            @Override
//            public boolean isAccountNonLocked()
//            {
//                return true;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired()
//            {
//                return true;
//            }
//
//            @Override
//            public boolean isEnabled()
//            {
//                return true;
//            }
//        };
//
//    }

    public static void main(String[] args)
    {
        final String adminek = new BCryptPasswordEncoder().encode("adminek");
        System.out.println(adminek);
    }
}
