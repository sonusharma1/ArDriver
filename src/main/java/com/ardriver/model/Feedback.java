package com.ardriver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @DecimalMin(value = "1.0", message = "rating should be > 0")
    @DecimalMax(value = "5.0", message = "rating should be <= 5")
    private double rating;
    @Column(length = 100)
    private String comments;

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                '}';
    }
}
