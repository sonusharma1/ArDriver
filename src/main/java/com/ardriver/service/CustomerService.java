package com.ardriver.service;

import com.ardriver.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    void saveCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer customerid);

    void findCustomersNameStartsWith(String customerName);

    void findCustomerRides(Integer customerId);

    List<Customer> findCustomerByNameOrEmail(String customerName, String email);

    List<Customer> findByEmailDomain(String emailDomain);

    List<Customer> findCustomersByName(String customerName);

    Customer findCustomerById(Integer id);

    List<Customer> findAllCustomers();

    // Transforming and Aggregating
    Map<Customer, Double> getCustomersWithTotalTraveledDist();
}
