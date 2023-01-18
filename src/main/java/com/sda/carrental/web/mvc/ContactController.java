package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final DepartmentService departmentService;

    @GetMapping
    public String welcomePage(final ModelMap map) {
        map.addAttribute("dealer", departmentService.findAllWhereCountry(Department.CountryCode.COUNTRY_PL));
        map.addAttribute("hq", departmentService.findAllWhereCountryCodeAndHq(Department.CountryCode.COUNTRY_PL));
        return "contact";
    }
}
