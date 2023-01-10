package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class WelcomeController {
    private final DepartmentService departmentService;
    @GetMapping
    public String welcomePage(final ModelMap map) {
        List<Department> department = departmentService.findAll();
        map.addAttribute("department", department);
        return "index";
    }
}