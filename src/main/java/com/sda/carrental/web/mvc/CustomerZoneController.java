package com.sda.carrental.web.mvc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/customerZone")
public class CustomerZoneController
{
    private final ReservationService reservationService;

    @GetMapping
    public String showReservation(final ModelMap map) {

        List<Reservation> reservationList = reservationService.findReservationByUser();


        map.addAttribute("reservations", reservationList);
        map.addAttribute("car", reservationList.stream().map(Reservation::getCar_id).distinct().collect(Collectors.toList()));
        map.addAttribute("dataFrom", reservationList.stream().map(Reservation::getDateFrom).distinct().collect(Collectors.toList()));//może lepiej typ
        map.addAttribute("dataTo", reservationList.stream().map(Reservation::getDateTo).distinct().collect(Collectors.toList()));//też można potencjalnie zmienić np zakres kosztu
        map.addAttribute("departmentTake", reservationList.stream().map(Reservation::getDepartmentTake).distinct().collect(Collectors.toList()));//TODO zmienić z koloru na ilość miejsc
        map.addAttribute("departmentBack", reservationList.stream().map(Reservation::getDepartmentBack).distinct().collect(Collectors.toList()));//TODO zmienić z koloru na ilość miejsc
        map.addAttribute("amountToPay", reservationList.stream().map(Reservation::getDepartmentBack).distinct().collect(Collectors.toList()));

        return "showReservations";
    }

    @GetMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable Long id) {

        reservationService.deleteReservationByReservationId(id);

        return "redirect:/customerZone";

    }
}
