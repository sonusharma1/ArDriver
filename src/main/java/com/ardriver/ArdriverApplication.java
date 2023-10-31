package com.ardriver;

import com.ardriver.repository.CustomerRepository;
import com.ardriver.service.*;
import com.ardriver.utility.PrintEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Level;


@ComponentScan
public class ArdriverApplication {

    public static final Logger log = LogManager.getLogger(ArdriverApplication.class);

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ApplicationContext context = new AnnotationConfigApplicationContext(ArdriverApplication.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        CarService carService = context.getBean(CarService.class);
        RideService rideService = context.getBean(RideService.class);
        FeedbackService feedbackService = context.getBean(FeedbackService.class);
        DriverService driverService = context.getBean(DriverService.class);
        PrintEntity printEntity = context.getBean(PrintEntity.class);
        System.out.println("Good morning");
        System.out.println("ROM ROM Bhaiyo");
        System.out.println("ROM ROM Bhaiyo");
        System.out.println("ROM ROM Bhaiyo");

        // Customer ----------------------------------------------------------------------------------
        // save customer
        /*Customer customer = Customer.builder()
                .customerName("Mahesh")
                .email("Mahesh@d.com")
                .mobileNo("7234567890")
                .password("mahesh123")
                .build();
        customerService.saveCustomer(customer);*/
        // customerService.findCustomersNameStartsWith("K");
        // customerService.findCustomerRides(9);
        /*customerService.updateCustomer(
                Customer.builder()
                        .customerId(13)
                        .customerName("Jiraya Sensei")
                        .email("jiraya@leafvillage.com")
                        .password("jiraya5454")
                        .build()
        );*/
        // customerService.findCustomerById(5);
        // printEntity.printCustomerDetail(customerService.findCustomersByName("Naruto"));
        // printEntity.printCustomerDetail(customerService.findAllCustomers());

        // Driver ----------------------------------------------------------------------------------------
        // Native Queries :
        /*Driver driver = Driver.builder()
                .driverId(24)
                .driverName("Martin")
                .licenceNo("5487544")
                .build();
        driverService.addDriver(driver);*/
        // driverService.findDriver(22);
        // driverService.updateDriverName(24, "David");
        // driverService.deleteDriverById(24);

        // Cars ----------------------------------------------------------------------------------
        // save cars
        /*Driver driver = Driver.builder()
                .driverName("James")
                .licenceNo("MH0420158889878")
                .build();
        Car car = Car.builder()
                .licenseNumberPlate("MH03AB7854")
                .carType(CarType.LUV)
                .latitude(19.187256884234895)
                .longitude(72.84744429332655)
                .build();
        carService.addCar(car,driver);*/

        // carService.findCarById(20);
        // carService.findCarByType(CarType.SUV);
        // printEntity.printCarDetails(carService.findNearByCab(19.20569723324068, 72.83501306491051));


        // Rides -----------------------------------------------------------------------------------
        /*rideService.bookRide(
                new Double[][]{
                        {19.215018852264453, 72.8491362784721}, // source location
                        {19.176543902214227, 72.86314216512362} // destination location
                },
                12
        );*/


        /*Feedback feedback = Feedback.builder()
                .comments("Driver deserves an award for best parallel universe navigation!")
                .rating(3)
                .build();
        feedbackService.giveFeedBack(4, feedback);*/
        // System.out.println(context.getBean(FeedbackRepository.class).findById(12));

        // rideService.findRidesByCarType(CarType.LUV);
        // rideService.findRidesByTraveledDistanceGreaterThan(200.0);


        // Criteria Builder -------------------------------------------------------------------
        // criteria builder =============>
        // printEntity.printCustomerDetail(customerService.findCustomerByNameOrEmail("Lee", "sensei"));
        // printEntity.printCustomerDetail(customerService.findByEmailDomain("leafvillage.com"));
        // specification ================>
        // printEntity.printCustomerDetail(customerService.findCustomersByName("Sa"));
        // rideService.findRidesByRatingGreaterThan(3.5);


        // ================================>
        // java 8
        // rideService.getCustomersWithHighRatings(4.0).forEach(System.out::println);
        // rideService.getAvgRatingOfDrivers().forEach((driverName, rating) -> System.out.println(driverName+"::"+rating));
        // driverService.getTopThreeDriverWithHighestRating().forEach(System.out::println);
        /*rideService.getTraveledDistanceWithCustomerRatingGreaterThan(3.5)
                .forEach((customer, traveledDist) -> System.out.println(customer.getCustomerName()+" => "+traveledDist));*/
        // rideService.getRidesOnBasisOfRating().forEach((rating,totalRides)-> System.out.println(Math.round(rating)+" => "+totalRides));
        /*customerService
                .getCustomersWithTotalTraveledDist() // returns map<customer, double>
                .forEach((customer, traveledDist) ->
                        System.out.println(customer.getCustomerName() + " ===> " + traveledDist)
                );*/

        /*rideService.getShortestAndLongestRide()
                .forEach((key, ride)->
                        System.out.println(key+" ===> "+ride.getTraveledDistance())
                );*/
        // System.out.println(rideService.findAvgTraveledDistByCarType());

//        context.getBean(CustomerRepository.class).deleteById(3);
    }
}