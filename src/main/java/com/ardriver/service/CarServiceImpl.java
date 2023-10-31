package com.ardriver.service;

import com.ardriver.ArdriverApplication;
import com.ardriver.model.Car;
import com.ardriver.model.Driver;
import com.ardriver.repository.CarRepository;
import com.ardriver.repository.DriverRepository;
import com.ardriver.utility.CarType;
import com.ardriver.utility.DistanceCalculator;
import com.ardriver.utility.PrintEntity;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ardriver.model.Ride_.car;

@Service
public class CarServiceImpl implements CarService {

    public static final Logger log = LogManager.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PrintEntity printEntities;

    @Autowired
    private EntityManager entityManager;

    //@Transactional
    @Override
    public void addCar(Car car, Driver driver) {
        try {
            car.setDriver(driver);
            carRepository.save(car);
            log.info("car details saved into DB :)");

        } catch (Exception exception) {

            log.error("car not saved!! something went wrong");
            while (exception.getCause() != null) {
                exception = (Exception) exception.getCause();
                log.error(exception.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public void findCarById(int id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            printEntities.printCarDetails(List.of(car.get()));
        } else {
            log.error("Car not found with id=" + id);
        }
    }

    @Override
    @Transactional
    public void findCarByType(CarType carType) {
        List<Car> carList = carRepository.findByCarType(carType);
        if (carList == null)
            log.warn("cars are not available of " + carType + " type");
        else
            printEntities.printCarDetails(carList);
    }

    @Override
    @Transactional
    public List<Car> findNearByCab(Double userLatitude, Double userLongitude) {
        double searchRadiusKm = 1.0;
        List<Car> carList = carRepository.findAll();

        carList = carList.stream().filter((car) -> {
            double distance = DistanceCalculator.haversineDistance(userLatitude, userLongitude, car.getLatitude(), car.getLongitude());
            return distance <= searchRadiusKm;
        }).collect(Collectors.toList());

        if (carList == null) {
            log.warn("No cars available at this location");
        }
        return carList;
    }

}
