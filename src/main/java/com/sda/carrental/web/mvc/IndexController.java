package com.sda.carrental.web.mvc;


import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.web.mvc.form.CreateIndexForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/")
public class IndexController {


    private final DepartmentService departmentService;
    @GetMapping
    public String indexPage(final ModelMap map) {
        map.addAttribute("department", departmentService.findAll());
        map.addAttribute("createIndexForm", new CreateIndexForm());
        return "index";
    }

    @PostMapping
    public String handleRequest(@ModelAttribute("createIndexForm") @Valid CreateIndexForm form, Errors errors, RedirectAttributes redirectAttributes, ModelMap map) {
        if(errors.hasErrors()) {
            map.addAttribute("department", departmentService.findAll());
            map.addAttribute("createIndexForm", new CreateIndexForm());
            return "index";
        }

        form.setDateCreated(LocalDate.now());

        redirectAttributes.addFlashAttribute("indexData", form);
        return "redirect:/show";
    }
}