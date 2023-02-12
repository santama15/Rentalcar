package com.sda.carrental.web.mvc;

import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.CarService;
import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.web.mvc.form.ShowCarsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/summary")
public class ReservationController {

    private final CarService carService;
    private final DepartmentService depService;

    @RequestMapping(method = RequestMethod.GET)
    public String sumReservationPage(final ModelMap map, @ModelAttribute("showData") ShowCarsForm reservationData) {
        if (reservationData == null) return "redirect:/";
        if (reservationData.getIndexData() == null) return "redirect:/";

        Car car = carService.findCarById(reservationData.getCar_id());
        Department depFrom = depService.findBranchWhereId(reservationData.getIndexData().getBranch_id_from());
        Department depTo = depService.findBranchWhereId(reservationData.getIndexData().getBranch_id_to());
        byte diffBranch = (byte) (reservationData.getIndexData().isFirstBranchChecked() ? 1 : 0);
        if (car == null || depFrom == null || depTo == null) return "redirect:/";

        map.addAttribute("days", (reservationData.getIndexData().getDateFrom().until(reservationData.getIndexData().getDateTo(), ChronoUnit.DAYS) + 1));
        map.addAttribute("diffBranch", diffBranch);
        map.addAttribute("branchFrom", depFrom);
        map.addAttribute("branchTo", depTo);
        map.addAttribute("reservationData", reservationData);
        map.addAttribute("car", car);
        return "sumReservation";
    }
}
