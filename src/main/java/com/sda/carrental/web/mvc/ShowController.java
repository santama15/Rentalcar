package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.web.mvc.form.CreateIndexForm;
import com.sda.carrental.web.mvc.form.CreateShowForm;
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
        map.addAttribute("brand", carList.stream().map(Car::getBrand).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("type", carList.stream().map(Car::getCarType).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("year", carList.stream().map(Car::getYear).distinct().sorted().collect(Collectors.toList())); //TODO Year -> Price range(?)
        map.addAttribute("seats", carList.stream().map(Car::getSeats).distinct().sorted().collect(Collectors.toList()));
        //można też potencjalnie poprawić query, żeby odrzucał status unavailable (?)

        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS) + 1));

        CreateShowForm showForm = new CreateShowForm();
        showForm.setIndexData(indexData);
        map.addAttribute("createShowForm", showForm);
        return "showResults";
    }

    @PostMapping
    public String showHandler(final ModelMap map, @ModelAttribute("createShowForm") CreateShowForm showData, @RequestParam(value="car_button") Long carId, RedirectAttributes redirectAttributes) {
        if(showData == null) return "redirect:/";
        showData.setCar_id(carId);
        if (showData.getIndexData() == null) return "redirect:/";


        redirectAttributes.addFlashAttribute("showData", showData);
        return "redirect:/summary";
    }
}