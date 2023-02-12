package com.sda.carrental.web.mvc;


import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.web.mvc.form.IndexForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/")
public class IndexController {


    private final DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public String indexPage(final ModelMap map) {
        map.addAttribute("department", departmentService.findAll());
        map.addAttribute("indexForm", new IndexForm());
        return "index";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String handleRequest(@ModelAttribute("indexForm") @Valid IndexForm form, Errors errors, RedirectAttributes redirectAttributes, ModelMap map) {
        if (errors.hasErrors()) {
            if (form.isFirstBranchChecked()) form.setFirstBranchChecked(false);

            map.addAttribute("indexForm", form);
            map.addAttribute("department", departmentService.findAll());
            return "index";
        }

        if (!form.isFirstBranchChecked()) form.setBranch_id_to(form.getBranch_id_from());
        if (form.isFirstBranchChecked()) {
            if (form.getBranch_id_from().equals(form.getBranch_id_to())) form.setFirstBranchChecked(false);
        }
        form.setDateCreated(LocalDate.now());

        redirectAttributes.addFlashAttribute("indexData", form);
        return "redirect:/cars";
    }
}