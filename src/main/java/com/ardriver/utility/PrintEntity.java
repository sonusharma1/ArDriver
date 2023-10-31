package com.ardriver.utility;

import com.ardriver.model.Car;
import com.ardriver.model.Customer;
import com.ardriver.model.Ride;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintEntity {

    public void printCarDetails(List<Car> cars) {
        String format = "%-10s %-25s %-15s %-45s %-40s%n";
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(format, "Car ID", "LicenseNumber Plate", "Car Type", "Current Location", "Driver Name");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Car car : cars) {
            System.out.format(format, car.getCarId(), car.getLicenseNumberPlate(), car.getCarType(),
                    car.getLatitude()+","+car.getLongitude(), car.getDriver().getDriverName());
        }
    }

    public void printRides(List<Ride> rides) {
        String format = "%-10s %-40s %-38s %-15s %-12s %-10s%n";
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.format(format, "Ride ID", "Source Location", "Dest Location", "Traveled Dist.", "Date", "Fare");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Ride ride : rides) {
            System.out.format(format, ride.getRideId(), ride.getSourceLocation(), ride.getDestLocation(),
                    ride.getTraveledDistance()+" Km", ride.getDate(), "₹ "+ride.getFare());
        }
    }

    public void printCustomerDetail(List<Customer> customers) {
        String format = "%-20s %-20s %-20s%n";
        System.out.println("--------------------------------------------------------------------");
        System.out.format(format, "Customer ID", "Customer Name", "Email");
        System.out.println("--------------------------------------------------------------------");

        for (Customer customer : customers) {
            System.out.format(format, customer.getCustomerId(), customer.getCustomerName(), customer.getEmail());
        }
    }
}
