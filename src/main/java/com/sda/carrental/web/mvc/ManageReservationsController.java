package com.sda.carrental.web.mvc;

import com.sda.carrental.constants.GlobalValues;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.PaymentDetails;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.service.CustomerService;
import com.sda.carrental.service.DepartmentService;
import com.sda.carrental.service.PaymentDetailsService;
import com.sda.carrental.service.ReservationService;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.SearchCustomerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mg-res")
public class ManageReservationsController {

    private final CustomerService customerService;
    private final DepartmentService departmentService;
    private final ReservationService reservationService;

    private final PaymentDetailsService paymentDetailsService;

    private final GlobalValues gv;


    @RequestMapping(method = RequestMethod.GET)
    public String searchCustomersPage(ModelMap map, RedirectAttributes redAtt) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            map.addAttribute("departments", departmentService.getDepartmentsByRole(cud));

            if (map.containsKey("searchCustomerForm")) {
                return "management/searchCustomers";
            } else {
                map.addAttribute("searchCustomerForm", new SearchCustomerForm());
            }
            return "management/searchCustomers";
        } catch (ResourceNotFoundException err) {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again.");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String customerSearchResults(@ModelAttribute("searchCustomerForm") SearchCustomerForm customersData, RedirectAttributes redAtt) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            redAtt.addFlashAttribute("searchCustomerForm", customersData);
            redAtt.addFlashAttribute("departments", departmentService.getDepartmentsByRole(cud));

            redAtt.addFlashAttribute("customers", customerService.findCustomersByDepartmentAndName(customersData));
            return "redirect:/mg-res";
        } catch (ResourceNotFoundException err) {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again.");
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public String customerReservationsButton(RedirectAttributes redAtt, @RequestParam("select_button") Long customerId, @RequestParam("department_id") Long departmentId) {
        redAtt.addAttribute("customer_id", customerId);
        redAtt.addFlashAttribute("department_id", departmentId);
        return "redirect:/mg-res/{customer_id}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{customer_id}")
    public String customerReservationsPage(final ModelMap map, RedirectAttributes redAtt, @PathVariable(value = "customer_id") Long customerId, @ModelAttribute("department_id") Long departmentId) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(HttpStatus.FORBIDDEN.equals(departmentService.departmentAccess(cud, departmentId))) {
                redAtt.addFlashAttribute("message", "Incorrect data. Access not allowed.");
                return "redirect:/mg-res";
            }

            Customer customer = customerService.findById(customerId);
            map.addAttribute("customer", customer);
            map.addAttribute("reservations", reservationService.getUserReservationsByDepartmentTake(customer.getEmail(), departmentId));
            return "management/reservationsManagement";
        } catch (ResourceNotFoundException err) {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again.");
            return "redirect:/mg-res";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/reservation")
    public String reservationsDetailButton(RedirectAttributes redAtt, @RequestParam("details_button") Long reservationId, @RequestParam("customer") Long customerId) {
        redAtt.addAttribute("reservation_id", reservationId);
        redAtt.addFlashAttribute("customer_id", customerId);
        return "redirect:/mg-res/reservation/{reservation_id}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reservation/{reservation_id}")
    public String reservationDetailsPage(final ModelMap map, RedirectAttributes redAtt, @PathVariable(value = "reservation_id") Long reservationId, @ModelAttribute("customer_id") Long customerId) {
        try {
            Customer customer = customerService.findById(customerId);
            Reservation reservation = reservationService.getUserReservation(customer.getEmail(), reservationId);

            if(paymentDetailsService.isReservationFined(reservation)) {
                PaymentDetails receipt = paymentDetailsService.findByReservation(reservation);
                map.addAttribute("diff_return_price", receipt.getRequiredReturnValue());
                map.addAttribute("raw_price", receipt.getRequiredRawValue());
                map.addAttribute("total_price", receipt.getRequiredRawValue()+receipt.getRequiredReturnValue());
                map.addAttribute("deposit_value", receipt.getRequiredDeposit());
            } else {
                long days = reservation.getDateFrom().until(reservation.getDateTo(), ChronoUnit.DAYS) + 1;
                if (!reservation.getDepartmentTake().equals(reservation.getDepartmentBack())) {
                    map.addAttribute("diff_return_price", gv.getDeptReturnPriceDiff());
                    map.addAttribute("total_price", gv.getDeptReturnPriceDiff() + (days * reservation.getCar().getPrice_day()));
                } else {
                    map.addAttribute("diff_return_price", 0.0);
                    map.addAttribute("total_price", days * reservation.getCar().getPrice_day());
                }
                map.addAttribute("raw_price", days * reservation.getCar().getPrice_day());
                map.addAttribute("deposit_value", reservation.getCar().getDepositValue());
            }

            map.addAttribute("reservation", reservation);
            map.addAttribute("deposit_percentage", gv.getDepositPercentage() * 100);
            map.addAttribute("refund_fee_days", gv.getRefundSubtractDaysDuration());
            return "management/reservationDetailsManagement";
        } catch (ResourceNotFoundException err) {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again later or contact customer service.");
            return "redirect:/mg-res";
        }
    }
}
