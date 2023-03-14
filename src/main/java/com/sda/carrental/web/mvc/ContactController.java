package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public String welcomePage(final ModelMap map) {
        map.addAttribute("dealer", departmentService.findAllWhereCountry(Department.CountryCode.COUNTRY_PL));
        map.addAttribute("hq", departmentService.findAllWhereCountryCodeAndHq(Department.CountryCode.COUNTRY_PL));
        return "core/contact";
    }
}
