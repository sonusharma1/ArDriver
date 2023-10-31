package com.ardriver.specification;

import com.ardriver.model.Customer;
import com.ardriver.model.Customer_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecifications {

    public static Specification<Customer> hasName(String customerName) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Customer_.CUSTOMER_NAME), customerName + "%");
        };
    }

    public static Specification<Customer> hasEmailDomain(String emailDomain) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Customer_.EMAIL), "%@" + emailDomain);
        };
    }

    public static Specification<Customer> hasNameOrEmail(String customerName, String email) {
        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get(Customer_.CUSTOMER_NAME), "%"+customerName+"%");
            Predicate emailPredicate = criteriaBuilder.like(root.get(Customer_.EMAIL), "%"+email+"%");
            return criteriaBuilder.or(namePredicate, emailPredicate);
        };
    }
}
