package com.sda.carrental.web.mvc;

import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.CarService;
import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.service.ReservationService;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.ShowCarsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/summary")
public class SummaryController {

    private final CarService carService;
    private final DepartmentService depService;
    private final ReservationService resService;

    @RequestMapping(method = RequestMethod.GET)
    public String sumReservationPage(final ModelMap map, @ModelAttribute("showData") ShowCarsForm reservationData, RedirectAttributes redAtt) {
        if (reservationData == null) return "redirect:/";
        if (reservationData.getIndexData() == null) return "redirect:/";

        try {
            Car car = carService.findCarById(reservationData.getCar_id());
            Department depFrom = depService.findBranchWhereId(reservationData.getIndexData().getBranch_id_from());
            Department depTo = depService.findBranchWhereId(reservationData.getIndexData().getBranch_id_to());
            byte diffBranch = (byte) (reservationData.getIndexData().isFirstBranchChecked() ? 1 : 0);

            map.addAttribute("days", (reservationData.getIndexData().getDateFrom().until(reservationData.getIndexData().getDateTo(), ChronoUnit.DAYS) + 1));
            map.addAttribute("diffBranch", diffBranch);
            map.addAttribute("branchFrom", depFrom);
            map.addAttribute("branchTo", depTo);
            map.addAttribute("reservationData", reservationData);
            map.addAttribute("car", car);
            return "reservationSummary";
        } catch (ResourceNotFoundException err) {
            err.printStackTrace();
            redAtt.addAttribute("response", "Błąd serwera! \nProsimy spróbować później lub skontaktować się telefonicznie.");
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String reservationConfirmation(final ModelMap model, @ModelAttribute("reservationData") ShowCarsForm reservationData, RedirectAttributes redAtt) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpStatus status = resService.createReservation(cud, reservationData.getCar_id(), reservationData.getIndexData());

        if (status == HttpStatus.CREATED) {
            model.addAttribute("response", "Rezerwacja została pomyślnie przesłana!");
            return "reservationsUser"; //TODO
        } else if (status == HttpStatus.CONFLICT) {
            redAtt.addAttribute("response", "Rezerwacja napotkała błąd przy tworzeniu. \nRezerwowany samochód mógł zostać zajęty lub podane dane są nieprawidłowe. \nW razie dalszych kłopotów prosimy skontaktować się telefonicznie.");
            return "redirect:/";
        } else {
            redAtt.addAttribute("response", "Błąd serwera! \nProsimy spróbować później lub skontaktować się telefonicznie.");
            return "redirect:/";
        }
    }

    //TODO create "response" handling in index HTML + check others
}
