package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.web.mvc.form.IndexForm;
import com.sda.carrental.web.mvc.form.ShowCarsForm;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/cars")
public class ShowCarsController {

    private final CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String showPage(final ModelMap map, @ModelAttribute("indexData") IndexForm indexData) {
        if (indexData.getDateCreated() == null) {
            return "redirect:/";
        }
        List<Car> carList = carService.findAvailableCarsInDepartment(
                indexData.getDateFrom(),
                indexData.getDateTo(),
                indexData.getBranch_id_from());

        map.addAttribute("cars", carList);
        map.addAttribute("brand", carList.stream().map(Car::getBrand).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("type", carList.stream().map(Car::getCarType).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("year", carList.stream().map(Car::getYear).distinct().sorted().collect(Collectors.toList())); //TODO Year -> Price range(?)
        map.addAttribute("seats", carList.stream().map(Car::getSeats).distinct().sorted().collect(Collectors.toList()));

        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS) + 1));

        ShowCarsForm showCarsForm = new ShowCarsForm();
        showCarsForm.setIndexData(indexData);
        map.addAttribute("showCarsForm", showCarsForm);
        return "showCars";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String showHandler(@ModelAttribute("showCarsForm") ShowCarsForm showCarsData, @RequestParam(value = "car_button") Long carId, RedirectAttributes redirectAttributes) {
        if (showCarsData == null) return "redirect:/";
        showCarsData.setCar_id(carId);
        if (showCarsData.getIndexData() == null) return "redirect:/";

        redirectAttributes.addFlashAttribute("showData", showCarsData);
        return "redirect:/summary";
    }
}