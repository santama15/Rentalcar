package com.sda.carrental.web.mvc;

import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.service.CarService;
import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.service.ReservationService;
import com.sda.carrental.web.mvc.form.CreateIndexForm;
import com.sda.carrental.web.mvc.form.CreateShowForm;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/show")
public class ShowController {

    private final CarService carService;
    private final ReservationService reservationService;
    private final DepartmentService departmentService;

    @GetMapping
    public String showPage(final ModelMap map, @ModelAttribute("indexData") CreateIndexForm indexData) {
        if (indexData.getDateCreated() == null) {
            return "redirect:/";
        }
        List<Car> carList = carService.findUnreservedCars(
                indexData.getDateFrom(),
                indexData.getDateTo(),
                indexData.getBranch_id_from());

        map.addAttribute("cars", carList);
        map.addAttribute("brand", carList.stream().map(Car::getBrand).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("type", carList.stream().map(Car::getCarType).distinct().sorted().collect(Collectors.toList()));
        map.addAttribute("year", carList.stream().map(Car::getYear).distinct().sorted().collect(Collectors.toList())); //TODO Year -> Price range(?)
        map.addAttribute("seats", carList.stream().map(Car::getSeats).distinct().sorted().collect(Collectors.toList()));
        //można też potencjalnie poprawić query, żeby odrzucał status unavailable (?)

        map.addAttribute("days", (indexData.getDateFrom().until(indexData.getDateTo(), ChronoUnit.DAYS) + 1));

        CreateShowForm showForm = new CreateShowForm();
        showForm.setIndexData(indexData);
        map.addAttribute("createShowForm", showForm);
        return "showResults";
    }

    @PostMapping()
//    public String showHandler(final ModelMap map, @ModelAttribute("createShowForm") CreateShowForm showData, @RequestParam(value="car_button") Long carId, RedirectAttributes redirectAttributes) {
//        if(showData == null) return "redirect:/";
//        showData.setCar_id(carId);
//        if (showData.getIndexData() == null) return "redirect:/";

        public String showHandler(final ModelMap map, @ModelAttribute("createShowForm") CreateShowForm showData,  @RequestParam(value="car_button") Car carId, RedirectAttributes redirectAttributes) {
        if(showData == null) return "redirect:/";
        showData.setCar_id(carId);
        if (showData.getIndexData() == null) return "redirect:/";

        Department departmentFrom = departmentService.findDepartmentByDepartmentId(showData.getIndexData().getBranch_id_from());

        redirectAttributes.addFlashAttribute("showData", showData);
        Reservation reservation = new Reservation();
        reservation.setDepartmentTake(departmentFrom);
        reservation.setDepartmentBack(showData.getIndexData().getBranch_id_to());
        reservation.setDateFrom(showData.getIndexData().getDateFrom());
        reservation.setDateTo(showData.getIndexData().getDateTo());
        reservation.setCar_id(showData.getCar_id());

        reservationService.save(reservation);


        return "redirect:/customerZone";
    }
}