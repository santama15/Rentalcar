package com.sda.carrental.web.mvc;

import com.sda.carrental.constants.GlobalValues;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.PaymentDetails;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Verification;
import com.sda.carrental.service.*;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.SearchCustomerForm;
import com.sda.carrental.web.mvc.form.VerificationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mg-res")
public class ManageReservationsController {

    private final CustomerService customerService;
    private final DepartmentService departmentService;
    private final ReservationService reservationService;
    private final PaymentDetailsService paymentDetailsService;
    private final VerificationService verificationService;

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
        redAtt.addAttribute("customer", customerId);
        redAtt.addFlashAttribute("department", departmentId);
        return "redirect:/mg-res/{customer}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{customer}")
    public String customerReservationsPage(final ModelMap map, RedirectAttributes redAtt, @PathVariable(value = "customer") Long customerId, @ModelAttribute("department") Long departmentId) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (HttpStatus.FORBIDDEN.equals(departmentService.departmentAccess(cud, departmentId))) {
                redAtt.addFlashAttribute("message", "Incorrect data. Access not allowed.");
                return "redirect:/mg-res";
            }

            Customer customer = customerService.findById(customerId);
            map.addAttribute("customer", customer);
            map.addAttribute("reservations", reservationService.getUserReservationsByDepartmentTake(customer.getEmail(), departmentId));

            Optional<Verification> verification = verificationService.getOptionalVerificationByCustomer(customer);
            map.addAttribute("is_verified", verification.isPresent());
            if (verification.isPresent()) {
                map.addAttribute("verification", verificationService.maskVerification(verification.get()));
            } else {
                map.addAttribute("verification", new Verification(customer, "N/D", "N/D"));
            }
            return "management/viewCustomerReservations";
        } catch (ResourceNotFoundException | IllegalStateException err) {
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

            if (paymentDetailsService.isReservationFined(reservation)) {
                PaymentDetails receipt = paymentDetailsService.findByReservation(reservation);
                map.addAttribute("diff_return_price", receipt.getRequiredReturnValue());
                map.addAttribute("raw_price", receipt.getRequiredRawValue());
                map.addAttribute("total_price", receipt.getRequiredRawValue() + receipt.getRequiredReturnValue());
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
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again.");
            return "redirect:/mg-res";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verify")
    public String verifyButton(RedirectAttributes redAtt, @RequestParam("customer") Long customerId, @RequestParam("department") Long departmentId) {
        redAtt.addFlashAttribute("customer", customerId);
        redAtt.addFlashAttribute("department", departmentId);
        return "redirect:/mg-res/verify";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/unverify")
    public String unverifyButton(RedirectAttributes redAtt, @RequestParam("customer") Long customerId, @RequestParam("department") Long departmentId) {
        HttpStatus status = verificationService.deleteByCustomerId(customerId);
        if(status.equals(HttpStatus.OK)) {
            redAtt.addAttribute("customer", customerId);
            redAtt.addFlashAttribute("department", departmentId);
            redAtt.addFlashAttribute("message", "Verification successfully removed");
        } else {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again.");
            return "redirect:/mg-res";
        }
        return "redirect:/mg-res/{customer}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public String verifyPage(ModelMap map, @ModelAttribute("customer") Long customerId, @ModelAttribute("department") Long departmentId) {
        map.addAttribute("verification_form", new VerificationForm(customerId));
        map.addAttribute("department", departmentId);
        return "management/verifyCustomer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verify/create")
    public String verifyConfirm(RedirectAttributes redAtt, @ModelAttribute("verification_form") @Valid VerificationForm form, Errors errors, @RequestParam("department") Long departmentId) {
        if (errors.hasErrors()) {
            redAtt.addFlashAttribute("department", departmentId);
            redAtt.addFlashAttribute("customer", form.getCustomerId());

            redAtt.addFlashAttribute("message", "Provided values are incorrect.");
            return "redirect:/mg-res/verify";
        }


        HttpStatus status = verificationService.createVerification(form);
        if (status.equals(HttpStatus.CREATED)) {
            redAtt.addFlashAttribute("message", "Verification successfully registered.");
        } else if (status.equals(HttpStatus.EXPECTATION_FAILED)) {
            redAtt.addFlashAttribute("message", "Customer is already verified.");
        } else if (status.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addFlashAttribute("message", "Customer not found. Try again.");
            return "redirect:/mg-res";
        }


        redAtt.addAttribute("customer", form.getCustomerId());
        redAtt.addFlashAttribute("department", departmentId);
        return "redirect:/mg-res/{customer}";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verify/back")
    public String verifyBack(RedirectAttributes redAtt, @ModelAttribute("verification_form") VerificationForm form, @RequestParam("department") Long departmentId) {
        redAtt.addAttribute("customer", form.getCustomerId());
        redAtt.addFlashAttribute("department", departmentId);
        return "redirect:/mg-res/{customer}";
    }
}
