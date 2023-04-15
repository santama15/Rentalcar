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
import java.util.Map;

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
                indexData.getDepartmentIdFrom());

        if (!map.containsKey("filteredCars")) {
            map.addAttribute("cars", carList);
        } else {
            map.addAttribute("cars", map.getAttribute("filteredCars"));
        }
        Map<String,Object> carProperties = carService.getFilterProperties(carList);

        map.addAttribute("brand", carProperties.get("brand"));
        map.addAttribute("type", carProperties.get("type"));
        map.addAttribute("seats", carProperties.get("seats"));

        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS) + 1));

        ShowCarsForm showCarsForm = new ShowCarsForm();
        CarFilterForm carFilterForm = new CarFilterForm();

        showCarsForm.setIndexData(indexData);
        carFilterForm.setIndexData(indexData);

        map.addAttribute("showCarsForm", showCarsForm);
        map.addAttribute("carFilterForm", carFilterForm);
        return "core/showCars";
    }

    @RequestMapping(value="/proceed", method = RequestMethod.POST)
    public String showHandler(@ModelAttribute("showCarsForm") ShowCarsForm showCarsData, @RequestParam(value = "car_button") Long carId, RedirectAttributes redirectAttributes) {
        if (showCarsData == null) return "redirect:/";
        showCarsData.setCarId(carId);
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