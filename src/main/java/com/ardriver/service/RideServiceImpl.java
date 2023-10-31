package com.ardriver.service;

import com.ardriver.model.Car;
import com.ardriver.model.Customer;
import com.ardriver.model.Ride;
import com.ardriver.repository.CarRepository;
import com.ardriver.repository.CustomerRepository;
import com.ardriver.repository.RideRepository;
import com.ardriver.specification.RideSpecifications;
import com.ardriver.utility.CarType;
import com.ardriver.utility.DistanceCalculator;
import com.ardriver.utility.PrintEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RideServiceImpl implements RideService {

    public static final Logger log = LogManager.getLogger(RideServiceImpl.class);

    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;
    @Autowired
    private Environment environment;
    @Autowired
    PrintEntity printEntity;

    @Override
    @Transactional
    public void bookRide(Double[][] location, Integer customerId) {
        Scanner sc = new Scanner(System.in);
        Double[] sourceLocation = location[0];
        Double[] destinationLocation = location[1];

        // finds nearby cars
        List<Car> nearByCars = carService.findNearByCab(sourceLocation[0], sourceLocation[1]);
        printEntity.printCarDetails(nearByCars);

        System.out.println("Enter car type :");
        String carType = sc.next().toUpperCase();

        // filters car type based on customer's choice
        nearByCars = nearByCars.stream().filter(car -> {
            return car.getCarType().equals(CarType.valueOf(carType));
        }).collect(Collectors.toList());

        // select a car for customer
        Car bookedCar = nearByCars.get(new Random().nextInt(nearByCars.size()));
        // calculates total travel distance
        Double traveledDist = DistanceCalculator.haversineDistance(sourceLocation[0], sourceLocation[1], destinationLocation[0], destinationLocation[1]);
        // calculates fare for that ride
        Double totalFare = traveledDist * bookedCar.getCarType().getFare();
        // applying discount on totalFare
        totalFare -= applyDiscount(customerId, totalFare);

        Ride ride = Ride.builder()
                .sourceLocation(sourceLocation[0] + "," + sourceLocation[1])
                .destLocation(destinationLocation[0] + "," + destinationLocation[1])
                .traveledDistance((double) Math.round(traveledDist * 100) / 100)
                .date(LocalDate.now())
                .fare((double) Math.round(totalFare))
                .car(bookedCar)
                .customer(customerRepository.findById(customerId).get())
                .build();

        Ride bookedRide = rideRepository.save(ride);
        if (bookedRide != null) {
            log.info("Ride booked successfully XD");
        } else {
            log.error("Something went wrong!!! Ride booking failed!!!");
        }
        printEntity.printRides(List.of(bookedRide));
    }

    private Double applyDiscount(Integer customerId, Double fareAmount) {
        double discountAmt = 0;
        int noOfRidesWithinAMonth = customerRepository.countRidesByCustomerId(customerId, LocalDate.now().minusMonths(1), LocalDate.now());
        if (noOfRidesWithinAMonth > 2) {
            discountAmt = fareAmount * 10 / 100;
            discountAmt = Math.round(discountAmt);
            log.info("""
                    ---------------------------------
                    Discount Applied -> Flat 10% off
                    ---------------------------------
                    """);
            return discountAmt;
        }

        return discountAmt;
    }

    @Override
    public void findRidesByCarType(CarType carType) {
        List<Ride> rides = rideRepository.findByCarType(carType);
        printEntity.printRides(rides);
    }

    @Override
    public void findRidesByTraveledDistanceGreaterThan(Double distance) {
        List<Ride> rides = rideRepository.findByTraveledDistanceLessThan(distance);
        printEntity.printRides(rides);
    }

    @Override
    public void findRidesByRatingGreaterThan(double rating) {
        List<Ride> rides = rideRepository.findAll(RideSpecifications.hasRatingGreaterThan(rating));
        printEntity.printRides(rides);
    }

    // used filter and map
    @Override
    public List<String> getCustomersWithHighRatings(Double rating) {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                .filter(ride -> ride.getFeedback() != null && ride.getFeedback().getRating() >= rating)
                .map(ride -> ride.getCustomer().getCustomerName())
                .collect(Collectors.toList());
    }

    // to find the average rating for each driver.
    @Override
    public Map<String, Double> getAvgRatingOfDrivers() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                .filter(ride -> ride.getFeedback() != null)
                .collect(Collectors.groupingBy(
                        ride -> ride.getCar().getDriver().getDriverName(),
                        Collectors.averagingDouble(ride -> ride.getFeedback().getRating())
                ));
    }

    // Mapping and Filtering
    @Override
    public Map<Customer, Double> getTraveledDistanceWithCustomerRatingGreaterThan(double rating) {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                .filter(ride -> ride.getFeedback() != null && ride.getFeedback().getRating() >= rating)
                .collect(Collectors.groupingBy(
                        Ride::getCustomer,
                        Collectors.summingDouble(Ride::getTraveledDistance)
                ));
    }

    // Partitioning and Counting
    @Override
    public Map<Double, Long> getRidesOnBasisOfRating() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                // .filter(ride -> ride.getFeedback()!=null)
                .collect(Collectors.groupingBy(
                        ride -> {
                            if (ride.getFeedback()==null)
                                return 0.0;
                            else
                                return ride.getFeedback().getRating();
                        },
                        Collectors.counting()
                ));
    }

    // Min and Max
    @Override
    public Map<String, Ride> getShortestAndLongestRide() {
        List<Ride> rides = rideRepository.findAll();
        Optional<Ride> shortesRide = rides.stream()
                .min(Comparator.comparing(Ride::getTraveledDistance));
        Optional<Ride> longestRide = rides.stream()
                .max(Comparator.comparing(Ride::getTraveledDistance));

        Map<String, Ride> shortestAndLongestRide = new HashMap<>();
        shortestAndLongestRide.put("shortestRide", shortesRide.get());
        shortestAndLongestRide.put("longestRide", longestRide.get());

        return shortestAndLongestRide;
    }

    @Override
    public Map<CarType, Double> findAvgTraveledDistByCarType() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream()
                .collect(Collectors.groupingBy(
                        ride -> ride.getCar().getCarType(),
                        Collectors.averagingDouble(Ride::getTraveledDistance)
                ));
    }

}
