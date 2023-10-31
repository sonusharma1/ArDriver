package com.ardriver.utility;

public enum CarType {
    AUTO(14.0),
    MINI(16.0),
    PRIME(18.0),
    SUV(20.0),
    LUV(50.0);

    private double fare;
    CarType(double fare) {
        this.fare = fare;
    }

    public double getFare() {
        return this.fare;
    }
}
