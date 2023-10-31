package com.ardriver.repository;

import com.ardriver.model.Ride;
import com.ardriver.utility.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride,Integer>, JpaSpecificationExecutor<Ride> {

    @Query("select r from Ride r WHERE r.car.carType = :carType")
    List<Ride> findByCarType(@Param("carType") CarType carType);

    List<Ride> findByTraveledDistanceLessThan(Double traveledDistance);

    /*@Query("select count from Rides ")
    Integer findNoOfRidesWithinAMonth(int customerId, LocalDate fromDate, LocalDate toDate);*/
}
