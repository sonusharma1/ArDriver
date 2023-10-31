package com.ardriver.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ride {

    @Id
    @SequenceGenerator(name = "ride_id_generator", sequenceName = "ride_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_id_generator")
    private Integer rideId;

    private String sourceLocation;
    private String destLocation;
    private Double traveledDistance;
    private LocalDate date;
    private Double fare;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Feedback feedback;

    @Override
    public String toString() {
        return "Ride{" +
                "rideId=" + rideId +
                ", sourceLocation='" + sourceLocation + '\'' +
                ", destLocation='" + destLocation + '\'' +
                ", traveledDistance=" + traveledDistance +
                ", date=" + date +
                ", fare=" + fare +
                //", car=" + car +
                //", customer=" + customer +
                //", feedback=" + feedback +
                '}';
    }
}
