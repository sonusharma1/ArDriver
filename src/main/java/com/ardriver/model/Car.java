package com.ardriver.model;

import com.ardriver.utility.CarType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.awt.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @SequenceGenerator(name = "car_id_generator", sequenceName = "car_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_generator")
    @Column(name = "car_id")
    private Integer carId;

    @Column(length = 10, nullable = false)
    @Pattern(regexp = "[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}", message = "Invalid Car Number Plate!!!")
    private String licenseNumberPlate;
    @NotNull
    private CarType carType;

    private Double latitude;
    private Double longitude;

    @OneToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @OneToMany(fetch = FetchType.LAZY)
    List<Ride> rides;

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", licenseNumberPlate='" + licenseNumberPlate + '\'' +
                ", carType=" + carType +
                //", driver=" + driver +
                //", rides=" + rides +
                '}';
    }
}
