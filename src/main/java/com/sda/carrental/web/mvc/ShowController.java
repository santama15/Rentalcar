package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.web.mvc.form.CreateIndexForm;
import com.sda.carrental.web.mvc.form.CreateShowResults;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/show")
public class ShowController {

    private final CarService carService;

    @GetMapping
    public String showPage(final ModelMap map, @ModelAttribute("indexData") CreateIndexForm indexData) {
        if (indexData.getDateCreated() == null) {
            return "redirect:/";
        }
        List<Car> carList = carService.findUnreservedCars(
                indexData.getDateFrom(),
                indexData.getDateTo(),
                indexData.getBranch_id_from());



        map.addAttribute("cars", carList);
        map.addAttribute("brand", carList.stream().map(Car::getBrand).distinct().collect(Collectors.toList()));
        map.addAttribute("model", carList.stream().map(Car::getModel).distinct().collect(Collectors.toList()));//może lepiej typ
        map.addAttribute("year", carList.stream().map(Car::getYear).distinct().collect(Collectors.toList()));//też można potencjalnie zmienić np zakres kosztu
        map.addAttribute("size", carList.stream().map(Car::getColor).distinct().collect(Collectors.toList()));//TODO zmienić z koloru na ilość miejsc
        //można też potencjalnie poprawić query, żeby odrzucał status unavailable (?)

        map.addAttribute("indexData", indexData);
        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS)+1));
        return "showResults";
    }

    @PostMapping
    public String showHandler(final ModelMap map, @ModelAttribute("blank") CreateShowResults showData) {
       return null;
    }
}