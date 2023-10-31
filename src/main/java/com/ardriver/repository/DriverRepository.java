package com.ardriver.repository;

import com.ardriver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {


    @Modifying
    @Query(value = "INSERT INTO driver (driverId, driverName, licenceNo, driverRating) VALUES(:driverId, :driverName, :licenceNo, 0)",
            nativeQuery = true)
    void addDriver(@Param("driverId") Integer driverId,
                   @Param("driverName") String driverName,
                   @Param("licenceNo") String licenceNo);

    @Query(value = "select * from driver where driverId = :id", nativeQuery = true)
    Driver findDriverById(@Param("id") Integer id);

    @Modifying
    @Query(value = "update driver set driverName = :driverName where driverId = :id", nativeQuery = true)
    void updateDriverName(@Param("id") Integer driverId, @Param("driverName") String driverName);

    @Modifying
    @Query(value = "delete from driver where driverId = :driverId", nativeQuery = true)
    void deleteDriver(@Param("driverId") Integer id);
}
