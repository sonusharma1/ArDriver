package com.ardriver.dao;

import com.ardriver.model.Customer;
import com.ardriver.model.Customer_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class CustomerDao {

    @Autowired
    private final EntityManager entityManager;

    public List<Customer> findCustomerByNameOrEmail(String customerName, String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);

        Root<Customer> root = criteriaQuery.from(Customer.class);
        Predicate namePredicate = criteriaBuilder.like(root.get(Customer_.CUSTOMER_NAME), "%"+customerName+"%");
        Predicate emailPredicate = criteriaBuilder.like(root.get(Customer_.EMAIL), "%"+email+"%");
        Predicate finalCondition = criteriaBuilder.or(namePredicate, emailPredicate);

        criteriaQuery.select(root).where(finalCondition);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Customer> findByEmailDomain(String emailDomain) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);

        Root<Customer> customerRoot = criteriaQuery.from(Customer.class);
        Predicate domainPredicate = criteriaBuilder.like(customerRoot.get(Customer_.EMAIL), "%@"+emailDomain);
        criteriaQuery.select(customerRoot).where(domainPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}