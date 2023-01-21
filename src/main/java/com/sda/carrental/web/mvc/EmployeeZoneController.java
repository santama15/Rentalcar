package com.sda.carrental.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employeeZone")
public class EmployeeZoneController
{
    @GetMapping
    public String welcomePage(final ModelMap map)
    {
        return "employeeZone";
    }
}
