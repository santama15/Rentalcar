package com.sda.carrental.clr;

import com.sda.carrental.model.Company;
import com.sda.carrental.model.operational.Renting;
import com.sda.carrental.model.operational.Reservation;
import com.sda.carrental.model.property.Car;
import com.sda.carrental.model.property.Department;
import com.sda.carrental.model.property.Invoice;
import com.sda.carrental.model.users.Customer;
import com.sda.carrental.model.users.Employee;
import com.sda.carrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class predefinedData implements CommandLineRunner {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final RentingRepository rentingRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public void run(String... args) throws Exception {
        createCompany();
        createDepartments();

        createUsers();
        createCars();

        createReservation();
        createRent();
        createInvoice();
    }

    private void createUsers() {
        userRepository.save(new Customer("user1@gmail.com", encoder.encode("password1"),"Anna", "Nazwiskowa", "ul. Ulica 123"));
        userRepository.save(new Customer("user2@gmail.com", encoder.encode("password1"),  "Jakub", "Kowalski", "ul. Ulica 12"));
        userRepository.save(new Customer("user3@gmail.com", encoder.encode("password1"),"Maciek", "Masło", "ul. Ulica 1"));
        userRepository.save(new Customer("user4@gmail.com", encoder.encode("password1"),"Jan", "Orzech", "ul. Ulica 124"));
        userRepository.save(new Customer("user5@gmail.com", encoder.encode("password1"),"Katarzyna", "Kasztan", "ul. Ulica 133"));
        userRepository.save(new Customer("user6@gmail.com", encoder.encode("password1"),"Igor", "Kasztan", "ul. Ulica 137"));
        userRepository.save(new Customer("user7@gmail.com", encoder.encode("password1"),"Anna", "Kowalska", "ul. Ulica 138"));
        userRepository.save(new Customer("a@a", encoder.encode("a"),"Andrzej", "Nowak", "ul. Ulica 139"));


        userRepository.save(new Employee("manager@gmail.com", encoder.encode("manager"), "Maria", "Fajna", departmentRepository.findById(1L).orElse(null), Employee.Titles.RANK_MANAGER));
        userRepository.save(new Employee("manager2@gmail.com", encoder.encode("manager2"), "Aleksandra", "Ładna", departmentRepository.findById(2L).orElse(null), Employee.Titles.RANK_MANAGER));
        userRepository.save(new Employee("manager3@gmail.com", encoder.encode("manager3"), "Katarzyna", "Nieładna", departmentRepository.findById(3L).orElse(null), Employee.Titles.RANK_MANAGER));
        userRepository.save(new Employee("pracownik@gmail.com", encoder.encode("pracownik"), "Anna", "Mniejfajna", departmentRepository.findById(1L).orElse(null), Employee.Titles.RANK_CLERK));
        userRepository.save(new Employee("pracownik2@gmail.com", encoder.encode("pracownik2"), "Magda", "Piąta", departmentRepository.findById(2L).orElse(null), Employee.Titles.RANK_CLERK));
        userRepository.save(new Employee("pracownik3@gmail.com", encoder.encode("pracownik3"), "Wioletta", "Fioletowa", departmentRepository.findById(3L).orElse(null), Employee.Titles.RANK_CLERK));
    }

    private void createCompany() {
        companyRepository.save(new Company("RentalCar", "www.rental-car.pl", "rental-car@gmail.com", "groupSIX", "RentalCar"));
    }

    private void createDepartments() {
        departmentRepository.save(new Department("PL", "Katowice", "ul. Fajna 1"));
        departmentRepository.save(new Department("PL", "Łódź", "ul. Niefajna 2"));
        departmentRepository.save(new Department("PL", "Gdańsk", "ul. Średnia 3"));
        departmentRepository.save(new Department("PL", "Warszawa", "ul. Pusta 4"));
        departmentRepository.save(new Department("PL", "Białystok", "ul. Pełna 5"));
        departmentRepository.save(new Department("PL", "Poznań", "ul. Półpełna 6"));
        departmentRepository.save(new Department("PL", "Wrocław", "ul. Półpusta 7"));
    }

    private void createCars() {
        carRepository.save(new Car(departmentRepository.findById(1L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(1L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(1L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(1L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));

        carRepository.save(new Car(departmentRepository.findById(2L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_UNAVAILABLE));
        carRepository.save(new Car(departmentRepository.findById(2L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(2L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_RENTED));
        carRepository.save(new Car(departmentRepository.findById(2L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_OPEN));

        carRepository.save(new Car(departmentRepository.findById(3L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(3L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_RENTED));
        carRepository.save(new Car(departmentRepository.findById(3L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(3L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));

        carRepository.save(new Car(departmentRepository.findById(4L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(4L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(4L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(4L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));

        carRepository.save(new Car(departmentRepository.findById(5L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(5L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(5L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_RENTED));
        carRepository.save(new Car(departmentRepository.findById(5L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));

        carRepository.save(new Car(departmentRepository.findById(6L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(6L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));
        carRepository.save(new Car(departmentRepository.findById(6L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_RENTED));
        carRepository.save(new Car(departmentRepository.findById(6L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));

        carRepository.save(new Car(departmentRepository.findById(7L).orElse(null), "https://cdn2.rcstatic.com/images/car_images/web/fiat/500_lrg.jpg", "Fiat", "Fiat 500", 2007, 150000L, Color.GRAY, 80, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(7L).orElse(null), "/cars/hyundai-elantra.jpg", "Hyundai", "Lantra", 1990, 120000L, Color.BLUE, 80, Car.CarType.TYPE_COMPACT, Car.CarStatus.STATUS_UNAVAILABLE));
        carRepository.save(new Car(departmentRepository.findById(7L).orElse(null), "/cars/bmw3.jpg", "BMW", "F34", 2013, 140000L, Color.BLUE, 82, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_OPEN));
        carRepository.save(new Car(departmentRepository.findById(7L).orElse(null), "/cars/yaris.png", "Toyota", "Yaris", 1999, 130000L, Color.RED, 81, Car.CarType.TYPE_HATCHBACK, Car.CarStatus.STATUS_RENTED));
    }

    private void createReservation() {
        //note: there should be created appropriate method for this
       reservationRepository.save(new Reservation((Customer) userRepository.findById(1L).orElse(null), carRepository.findById(7L).orElse(null), departmentRepository.findById(2L).orElse(null), departmentRepository.findById(3L).orElse(null), LocalDate.now().minusDays(1), LocalDate.now().plusDays(3), LocalDate.of(2022, 12, 6)));
       reservationRepository.save(new Reservation((Customer) userRepository.findById(2L).orElse(null), carRepository.findById(10L).orElse(null), departmentRepository.findById(3L).orElse(null), departmentRepository.findById(2L).orElse(null), LocalDate.now().minusDays(3), LocalDate.now().plusDays(1), LocalDate.of(2022, 12, 6)));
       reservationRepository.save(new Reservation((Customer) userRepository.findById(3L).orElse(null), carRepository.findById(19L).orElse(null), departmentRepository.findById(5L).orElse(null), departmentRepository.findById(7L).orElse(null), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), LocalDate.of(2022, 12, 6)));
       reservationRepository.save(new Reservation((Customer) userRepository.findById(4L).orElse(null), carRepository.findById(23L).orElse(null), departmentRepository.findById(6L).orElse(null), departmentRepository.findById(5L).orElse(null), LocalDate.now().plusDays(0), LocalDate.now().plusDays(4), LocalDate.of(2022, 12, 6)));
       reservationRepository.save(new Reservation((Customer) userRepository.findById(5L).orElse(null), carRepository.findById(28L).orElse(null), departmentRepository.findById(7L).orElse(null), departmentRepository.findById(6L).orElse(null), LocalDate.now().minusDays(2), LocalDate.now().plusDays(8), LocalDate.of(2022, 12, 6)));
  }
    private void createRent() {
        rentingRepository.save(new Renting(userRepository.findById(12L).orElse(null), reservationRepository.findById(1L).orElse(null), reservationRepository.findById(1L).get().getDateFrom(), ""));
        rentingRepository.save(new Renting(userRepository.findById(13L).orElse(null), reservationRepository.findById(2L).orElse(null), reservationRepository.findById(2L).get().getDateFrom(), ""));
        rentingRepository.save(new Renting(userRepository.findById(14L).orElse(null), reservationRepository.findById(3L).orElse(null), reservationRepository.findById(3L).get().getDateFrom(), ""));
        rentingRepository.save(new Renting(userRepository.findById(14L).orElse(null), reservationRepository.findById(4L).orElse(null), reservationRepository.findById(4L).get().getDateFrom(), ""));
        rentingRepository.save(new Renting(userRepository.findById(13L).orElse(null), reservationRepository.findById(5L).orElse(null), reservationRepository.findById(5L).get().getDateFrom(), ""));
    }
    private void createInvoice() {
        invoiceRepository.save(new Invoice(carRepository.findById(7L).get().getPrice_day()*(reservationRepository.findById(1L).get().getDateFrom().until(reservationRepository.findById(1L).get().getDateTo(), ChronoUnit.DAYS)), reservationRepository.findById(1L).orElse(null), true, false, LocalDate.now().plusMonths(1)));
        invoiceRepository.save(new Invoice(carRepository.findById(10L).get().getPrice_day()*(reservationRepository.findById(2L).get().getDateFrom().until(reservationRepository.findById(2L).get().getDateTo(), ChronoUnit.DAYS)), reservationRepository.findById(2L).orElse(null), true, false, LocalDate.now().plusMonths(1)));
        invoiceRepository.save(new Invoice(carRepository.findById(19L).get().getPrice_day()*(reservationRepository.findById(3L).get().getDateFrom().until(reservationRepository.findById(3L).get().getDateTo(), ChronoUnit.DAYS)), reservationRepository.findById(3L).orElse(null), true, false, LocalDate.now().plusMonths(1)));
        invoiceRepository.save(new Invoice(carRepository.findById(23L).get().getPrice_day()*(reservationRepository.findById(4L).get().getDateFrom().until(reservationRepository.findById(4L).get().getDateTo(), ChronoUnit.DAYS)), reservationRepository.findById(4L).orElse(null), true, false, LocalDate.now().plusMonths(1)));
        invoiceRepository.save(new Invoice(carRepository.findById(28L).get().getPrice_day()*(reservationRepository.findById(5L).get().getDateFrom().until(reservationRepository.findById(5L).get().getDateTo(), ChronoUnit.DAYS)), reservationRepository.findById(5L).orElse(null), true, false, LocalDate.now().plusMonths(1)));
    }
}
