package com.ardriver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Driver {

    @Id
    @SequenceGenerator(name = "driver_id_generator", sequenceName = "driver_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_id_generator")
    private Integer driverId;

    @Column(length = 30, nullable = false)
    @Size(min = 3, max = 30, message = "customer name must be between 3-30 character!")
    private String driverName;

    @Column(length = 15, nullable = false)
    @Pattern(regexp = "([A-Z]{2}[0-9]{2}(19|20)[0-9]{9})", message = "invalid DL No.!!!")
    private String licenceNo;

    private Double driverRating;

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", licenceNo='" + licenceNo + '\'' +
                ", driverRating=" + driverRating +
                '}';
    }
}
