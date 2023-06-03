package com.sda.carrental.web.mvc;

import com.sda.carrental.constants.GlobalValues;
import com.sda.carrental.exceptions.ResourceNotFoundException;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.PaymentDetails;
import com.sda.carrental.service.PaymentDetailsService;
import com.sda.carrental.service.ReservationService;
import com.sda.carrental.service.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class CustomerReservationsController {
    private final ReservationService reservationService;
    private final PaymentDetailsService paymentDetailsService;
    private final GlobalValues gv;

    @RequestMapping(method = RequestMethod.GET)
    public String reservationsPage(ModelMap map) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map.addAttribute("reservations", reservationService.getUserReservations(cud));
        return "user/reservationsCustomer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String reservationsDetailButton(RedirectAttributes redAtt, @RequestParam("details_button") Long reservation_id) {
        redAtt.addAttribute("details_button", reservation_id);
        return "redirect:/reservations/{details_button}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{details_button}")
    public String reservationDetailsPage(final ModelMap map, RedirectAttributes redAtt, @PathVariable Long details_button) {
        try {
            CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Reservation reservation = reservationService.getUserReservation(cud, details_button);

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
            return "user/reservationDetailsCustomer";
        } catch (ResourceNotFoundException err) {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again later or contact customer service.");
            return "redirect:/reservations";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/action")
    public String reservationRefundButton(RedirectAttributes redAtt, @RequestParam(value = "action") String action, @RequestParam(value = "id") Long reservation_id) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpStatus response;
        if (action.equals("refund")) {
            response = reservationService.setUserReservationStatus(cud, reservation_id, Reservation.ReservationStatus.STATUS_REFUNDED);
        } else {
            response = HttpStatus.BAD_REQUEST;
        }
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addFlashAttribute("message", "Return completed successfully!");
            return "redirect:/reservations";
        } else {
            redAtt.addFlashAttribute("message", "An unexpected error occurred. Please try again later or contact customer service.");
            return "redirect:/";
        }
    }
}
