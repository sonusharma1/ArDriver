package com.ardriver.service;

import com.ardriver.model.Feedback;
import org.springframework.transaction.annotation.Transactional;

public interface FeedbackService {
    void giveFeedBack(Integer rideId, Feedback feedback);
}
