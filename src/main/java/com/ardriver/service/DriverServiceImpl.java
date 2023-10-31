package com.ardriver.service;

import com.ardriver.model.Driver;
import com.ardriver.repository.DriverRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    public static final Logger LOGGER = LogManager.getLogger(DriverServiceImpl.class);
    @Autowired
    private DriverRepository driverRepository;

    @Transactional
    @Override
    public void addDriver(Driver driver) {
        driverRepository.addDriver(driver.getDriverId(), driver.getDriverName(), driver.getLicenceNo());
        LOGGER.info("Driver's detail saved :)");
    }

    //@Transactional
    @Override
    public void findDriver(Integer id) {
        Driver driver = driverRepository.findDriverById(id);
        LOGGER.info(driver);
    }

    @Transactional
    @Override
    public void updateDriverName(Integer driverId, String driverName) {
        Driver driver = driverRepository.findDriverById(driverId);
        if (driver != null) {
            driverRepository.updateDriverName(driverId, driverName);
            LOGGER.info("Driver's name changed :)");
        }
    }

    @Transactional
    @Override
    public void deleteDriverById(Integer driverId) {
        driverRepository.deleteDriver(driverId);
        LOGGER.info("Driver's detail deleted :)");
    }

    // Sorting and Limiting
    @Override
    public List<Driver> getTopThreeDriverWithHighestRating() {
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream()
                .sorted(
                        (d1,d2) -> d2.getDriverRating().compareTo(d1.getDriverRating())
                )
                .limit(3)
                .collect(Collectors.toList());
    }
}
