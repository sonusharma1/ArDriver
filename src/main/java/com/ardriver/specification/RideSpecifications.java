package com.ardriver.specification;

import com.ardriver.model.Feedback;
import com.ardriver.model.Feedback_;
import com.ardriver.model.Ride;
import com.ardriver.model.Ride_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RideSpecifications {

    public static Specification<Ride> hasRatingGreaterThan(double rating) {
        return (root, query, criteriaBuilder) -> {
            //Join<Ride, Feedback> feedbackJoin = root.join(Ride_.FEEDBACK);
            return criteriaBuilder.greaterThan(root.get(Ride_.FEEDBACK).get(Feedback_.RATING), rating);
        };
    }
}
