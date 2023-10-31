package com.ardriver.service;

import com.ardriver.model.Feedback;
import com.ardriver.model.Ride;
import com.ardriver.repository.FeedbackRepository;
import com.ardriver.repository.RideRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    public static final Logger log = LogManager.getLogger(FeedbackServiceImpl.class);

    @Transactional
    @Override
    public void giveFeedBack(Integer rideId, Feedback feedback) {
        try {
            Ride ride = rideRepository.findById(rideId).get();
            ride.setFeedback(feedbackRepository.save(feedback));

            // to update driver's overall rating -----------------------
            // calculates avg of ratings
            Double avgRatingOfDriver = feedbackRepository.findAvgRatingOfDriver(ride.getCar().getCarId());
            // rounds avg rating up-to 1 decimal
            avgRatingOfDriver = Double.parseDouble(String.format("%.1f", avgRatingOfDriver));
            // update overall rating of driver
            ride.getCar().getDriver().setDriverRating(avgRatingOfDriver);

            log.info("Thank you for your valuable feedback :)");

        } catch (Exception ex) {
            log.error("Something went wrong!!! Feedback not saved");
            log.error(ex.getMessage());
        }
    }

    /*@Transactional
    public void deleteAllFeedback() {
        feedbackRepository.deleteAll();
    }*/
}
