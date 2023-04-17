package com.sda.carrental.web.mvc;

import com.sda.carrental.constants.enums.Country;
import com.sda.carrental.service.CustomerService;
import com.sda.carrental.service.UserService;
import com.sda.carrental.service.VerificationService;
import com.sda.carrental.service.auth.CustomUserDetails;
import com.sda.carrental.web.mvc.form.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final CustomerService customerService;
    private final UserService userService;
    private final VerificationService verificationService;

    @RequestMapping(method = RequestMethod.GET)
    public String profilePage(ModelMap map) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map.addAttribute("customer", customerService.findByUsername(cud.getUsername()));
        map.addAttribute("verification", verificationService.isVerified(cud));
        return "user/profileCustomer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/password")
    public String changePasswordPage(final ModelMap map) {
        map.addAttribute("password_form", new ChangePasswordForm());
        return "user/passwordCustomer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email")
    public String changeEmailPage(final ModelMap map) {
        map.addAttribute("email_form", new ChangeEmailForm());
        return "user/emailCustomer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contact")
    public String changeContactPage(final ModelMap map) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map.addAttribute("customer", customerService.findByUsername(cud.getUsername()));
        map.addAttribute("contact_form", new ChangeContactForm());
        return "user/contactCustomer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public String deleteAccountPage(final ModelMap map) {
        CustomUserDetails cud = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map.addAttribute("user", cud.getUsername());
        map.addAttribute("delete_form", new DeleteAccountForm());
        return "user/deleteCustomer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/address")
    public String changeAddressPage(final ModelMap map) {
        map.addAttribute("countries", Country.values());
        map.addAttribute("address_form", new ChangeAddressForm());
        return "user/addressCustomer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact")
    public String changeContactAction(RedirectAttributes redAtt, @ModelAttribute("contact_form") @Valid ChangeContactForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "user/contactCustomer";
        }

        HttpStatus response = customerService.changeContact(form.getContactNumber());
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addAttribute("message", "Numer kontaktowy został pomyślnie zmieniony.");
            return "redirect:/profile";
        } else if (response.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addAttribute("message", "Użytkownik nie został rozpoznany. Prosimy zalogować się ponownie.");
        } else {
            redAtt.addAttribute("message", "Wystąpił nieoczekiwany błąd. Prosimy spróbować później lub skontaktować się z obsługą klienta.");
        }
        return "redirect:/profile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/address")
    public String changeAddressAction(RedirectAttributes redAtt, @ModelAttribute("address_form") @Valid ChangeAddressForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "user/addressCustomer";
        }

        HttpStatus response = customerService.changeAddress(form);
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addAttribute("message", "Adres korespondencyjny został pomyślnie zmieniony.");
            return "redirect:/profile";
        } else if (response.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addAttribute("message", "Użytkownik nie został rozpoznany. Prosimy zalogować się ponownie.");
        } else {
            redAtt.addAttribute("message", "Wystąpił nieoczekiwany błąd. Prosimy spróbować później lub skontaktować się z obsługą klienta.");
        }
        return "redirect:/profile";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/email")
    public String changeEmailAction(RedirectAttributes redAtt, @ModelAttribute("email_form") @Valid ChangeEmailForm form, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "user/emailCustomer";
        }

        HttpStatus response = userService.changeEmail(form.getNewEmail());
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addAttribute("message", "E-mail został pomyślnie zmieniony. Prosimy zalogować się ponownie.");
            request.getSession().invalidate();
            return "redirect:/";
        } else if (response.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addAttribute("message", "Użytkownik nie został rozpoznany. Prosimy zalogować się ponownie.");
        } else {
            redAtt.addAttribute("message", "Wystąpił nieoczekiwany błąd. Prosimy spróbować później lub skontaktować się z obsługą klienta.");
        }
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/password")
    public String changePasswordAction(RedirectAttributes redAtt, @ModelAttribute("password_form") @Valid ChangePasswordForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "user/passwordCustomer";
        }

        HttpStatus response = userService.changePassword(form.getNewPassword());
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addAttribute("message", "Hasło zostało pomyślnie zmienione.");
            return "redirect:/profile";
        } else if (response.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addAttribute("message", "Użytkownik nie został rozpoznany.");
        } else {
            redAtt.addAttribute("message", "Wystąpił nieoczekiwany błąd. Prosimy spróbować później lub skontaktować się z obsługą klienta.");
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String deleteAccountAction(RedirectAttributes redAtt, @ModelAttribute("delete_form") @Valid DeleteAccountForm form, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "user/deleteCustomer";
        }

        HttpStatus response = customerService.scrambleCustomer();
        if (response.equals(HttpStatus.ACCEPTED)) {
            redAtt.addAttribute("message", "Konto zostało pomyślnie skasowane.");
            request.getSession().invalidate();
            return "redirect:/";
        } else if (response.equals(HttpStatus.NOT_FOUND)) {
            redAtt.addAttribute("message", "Użytkownik nie został rozpoznany. Prosimy zalogować się ponownie.");
        } else {
            redAtt.addAttribute("message", "Wystąpił nieoczekiwany błąd. Prosimy spróbować później lub skontaktować się z obsługą klienta.");
        }
        request.getSession().invalidate();
        return "redirect:/";
    }
}
