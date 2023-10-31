package com.ardriver.service;

import com.ardriver.model.Car;
import com.ardriver.model.Driver;
import com.ardriver.utility.CarType;

import java.util.List;

public interface CarService {

    void addCar(Car car, Driver driver);

    void findCarById(int id);

    void findCarByType(CarType carType);

    List<Car> findNearByCab(Double userLatitude, Double userLongitude);
}
