package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.service.CarService;
import com.sda.carrental.web.mvc.form.CreateIndexForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/show")
public class ShowController {

    private final CarService carService;
    @GetMapping
    public String showPage(final ModelMap map, @ModelAttribute("indexData") CreateIndexForm indexData) {
        if(indexData == null) {
            return "redirect:/";
        }
        List<Car> cars = carService.findAll();
        map.addAttribute("cars", carService.findAll());
        map.addAttribute("indexData", indexData);
        return "showResults";
    }
}