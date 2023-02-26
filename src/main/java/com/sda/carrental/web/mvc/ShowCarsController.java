package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.web.mvc.form.CarFilterForm;
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

        if (!map.containsKey("filteredCars")) {
            map.addAttribute("cars", carList);
        } else {
            map.addAttribute("cars", map.getAttribute("filteredCars"));
        }

        map.addAttribute("brand", carList.stream().map(Car::getBrand).distinct().sorted().collect(Collectors.toList())); //todo clean this part in code
        map.addAttribute("type", carList.stream().map(Car::getCarType).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("seats", carList.stream().map(Car::getSeats).distinct().sorted().collect(Collectors.toList()));

        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS) + 1));

        ShowCarsForm showCarsForm = new ShowCarsForm();
        CarFilterForm carFilterForm = new CarFilterForm();

        showCarsForm.setIndexData(indexData);
        carFilterForm.setIndexData(indexData);

        map.addAttribute("showCarsForm", showCarsForm);
        map.addAttribute("carFilterForm", carFilterForm);
        return "showCars";
    }

    @RequestMapping(value="/proceed", method = RequestMethod.POST)
    public String showHandler(@ModelAttribute("showCarsForm") ShowCarsForm showCarsData, @RequestParam(value = "car_button") Long carId, RedirectAttributes redirectAttributes) {
        if (showCarsData == null) return "redirect:/";
        showCarsData.setCar_id(carId);
        if (showCarsData.getIndexData() == null) return "redirect:/";

        redirectAttributes.addFlashAttribute("showData", showCarsData);
        return "redirect:/summary";
    }

    @RequestMapping(value="/filter", method = RequestMethod.POST)
    public String filterCars(@ModelAttribute("carFilterForm") CarFilterForm filterData, RedirectAttributes redirect) {
        redirect.addFlashAttribute("filteredCars", carService.filterCars(filterData));
        redirect.addFlashAttribute("indexData", filterData.getIndexData());
        return "redirect:/cars";
    }
}