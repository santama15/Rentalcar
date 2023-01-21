package com.sda.carrental.service;


import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.users.User;
import com.sda.carrental.repository.ReservationRepository;
import com.sda.carrental.repository.UserRepository;
import com.sda.carrental.service.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ReservationService
{


    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;



    public List<Reservation> findReservationByUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String emailLoggedUser = auth.getName();
        Long userId = userRepository.findByEmail(emailLoggedUser).get().getId();
        return StreamSupport.stream(reservationRepository.findReservationByCustomerId(userId).spliterator(),
            false).collect(Collectors.toList());
    }

//    public User save(User user) {
//        user.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
//        user.setPassword((user.getPassword()));
//        return repository.save(user);
//    }

//    public void deleteReservationById(Long id) {reservationRepository.deleteById(id);}
    public void deleteReservationByReservationId(Long id) {reservationRepository.deleteReservationByReservation_id(id);}

}

