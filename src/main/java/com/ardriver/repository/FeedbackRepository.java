package com.ardriver.repository;

import com.ardriver.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    @Query("select avg(f.rating) from Ride r, Feedback f where r.feedback.feedbackId = f.feedbackId and r.car.carId = :carId")
    Double findAvgRatingOfDriver(@Param("carId") int carId);
}
