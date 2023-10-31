package com.ardriver.service;

import com.ardriver.dao.CustomerDao;
import com.ardriver.model.Customer;
import com.ardriver.model.Ride;
import com.ardriver.repository.CustomerRepository;
import com.ardriver.specification.CustomerSpecifications;
import com.ardriver.utility.PrintEntity;
import jakarta.validation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class CustomerServiceImpl implements CustomerService {

    public static final Logger log = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PrintEntity printEntity;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void saveCustomer(@Valid Customer customer) {
        try {
            Customer savedCustomer = customerRepository.save(customer);
            log.info("customer saved :)");
        }
        // if any error occurs
        catch (Exception ex) {
            log.error("customer not saved!!");

            while (ex.getCause() != null) {
                ex = (Exception) ex.getCause();
                /*if (ex instanceof ConstraintViolationException) {
                    Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
                    constraintViolations.forEach(error -> log.error(error.getMessage()));
                }*/
                log.error(ex.getMessage());
            }
        }
    }

    @Transactional
    @Override
    public void updateCustomer(@Valid Customer customer) {
        Customer foundCustomer = customerRepository.findById(customer.getCustomerId()).get();
        if (foundCustomer != null) {
            BeanUtils.copyProperties(customer, foundCustomer);
            log.info("Customer details updated");
        } else {
            log.error("Customer not found");
        }
    }

    @Transactional
    @Override
    public void deleteCustomer(Integer customerid) {
        customerRepository.deleteById(customerid);
    }

    @Override
    public void findCustomersNameStartsWith(String customerName) {
        printEntity.printCustomerDetail(customerRepository.findByCustomerNameLike(customerName + "%"));
    }

    @Transactional
    @Override
    public void findCustomerRides(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            printEntity.printCustomerDetail(List.of(customer.get()));
            List<Ride> rides = customer.get().getRides();
            printEntity.printRides(rides);
        } else {
            log.error("Customer not found with id=" + customerId);
        }
    }

    @Override
    public List<Customer> findCustomerByNameOrEmail(String customerName, String email) {
        // return customerDao.findCustomerByNameOrEmail(customerName, email);
        return customerRepository.findAll(CustomerSpecifications.hasNameOrEmail(customerName, email));
    }

    @Override
    public List<Customer> findByEmailDomain(String emailDomain) {
        // return customerDao.findByEmailDomain(emailDomain);
        // OR
        return customerRepository.findAll(CustomerSpecifications.hasEmailDomain(emailDomain));
    }

    @Override
    public List<Customer> findCustomersByName(String customerName) {
        List<Customer> customerList = customerRepository.findAll(CustomerSpecifications.hasName(customerName));
        return customerList;
    }

    @Override
    public Customer findCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).get();
        printEntity.printCustomerDetail(List.of(customer));
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    // Transforming and Aggregating
    @Override
    public Map<Customer, Double> getCustomersWithTotalTraveledDist() {
        List<Customer> customers = customerRepository.findAll();

        return customers
                .stream()
                .collect(Collectors.toMap(
                        customer -> customer, // KEY
                        customer -> customer.getRides() // VALUE (returns sum(customer.List<Rides>.traveledDist)
                                .stream()
                                .mapToDouble(Ride::getTraveledDistance)
                                .sum()
                ));
    }
}
