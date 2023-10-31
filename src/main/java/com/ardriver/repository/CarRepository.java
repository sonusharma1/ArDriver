package com.ardriver.repository;

import com.ardriver.model.Car;
import com.ardriver.utility.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByCarType(CarType carType);

    @Query(value = "select c.*, d.* from car c, driver d where c.driver_driverId = d.driverId", nativeQuery = true)
     List<Car> findCars();


}
