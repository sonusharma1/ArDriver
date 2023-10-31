package com.ardriver.service;

import com.ardriver.model.Driver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverService {
    void addDriver(Driver driver);

    void findDriver(Integer id);

    void updateDriverName(Integer driverId, String driverName);

    @Transactional
    void deleteDriverById(Integer driverId);

    // Sorting and Limiting
    List<Driver> getTopThreeDriverWithHighestRating();
}
