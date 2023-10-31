package com.ardriver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_id_generator", sequenceName = "customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name", length = 30, nullable = false)
    @Size(min = 3, max = 30, message = "customer name must be between 3-30 character!")
    private String customerName;

    @Column(length = 50, nullable = false)
    @Email
    private String email;

    @Column(length = 10)
    @Pattern(regexp = "[6-9]\\d{9}")
    private String mobileNo;

    @Column(length = 15, nullable = false)
    // @Pattern(regexp = "")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ride> rides;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.customerName = builder.customerName;
        this.email = builder.email;
        this.mobileNo = builder.mobileNo;
        this.password = builder.password;
        this.rides = builder.rides;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer customerId;
        private String customerName;
        private String email;
        private String mobileNo;
        private String password;
        private List<Ride> rides;

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }
        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder rides(List<Ride> rides) {
            this.rides = rides;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rides=" + rides +
                '}';
    }
}
