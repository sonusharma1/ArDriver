package com.ardriver.service;

import com.ardriver.model.Customer;
import com.ardriver.model.Ride;
import com.ardriver.utility.CarType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RideService {

    void bookRide(Double[][] location, Integer customerId);

    void findRidesByCarType(CarType carType);

    void findRidesByTraveledDistanceGreaterThan(Double distance);

    void findRidesByRatingGreaterThan(double rating);

    List<String> getCustomersWithHighRatings(Double rating);

    // to find the average rating for each driver.
    Map<String, Double> getAvgRatingOfDrivers();

    // Mapping and Filtering
    Map<Customer, Double> getTraveledDistanceWithCustomerRatingGreaterThan(double rating);

    // Partitioning and Counting
    Map<Double, Long> getRidesOnBasisOfRating();

    // Min and Max
    Map<String, Ride> getShortestAndLongestRide();

    Map<CarType, Double> findAvgTraveledDistByCarType();
}
