package com.ardriver.repository;

import com.ardriver.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
    @Query("SELECT COUNT(*) FROM Customer c, Ride r WHERE c.id = r.customer.id AND c.customerId = :customerId " +
            "AND r.date BETWEEN :fromDate AND :toDate")
    Integer countRidesByCustomerId(@Param("customerId") int customerId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    List<Customer> findByCustomerNameLike(String customerName);
}
