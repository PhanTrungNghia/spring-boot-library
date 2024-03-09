package com.luv2code.springbootlibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders ")
@Data
public class Order {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "total_cash")
    private Double totalCash;
    @Column(name = "status")
    private String status;
    @Column(name = "create_date")
    private String createDate;
    @Column(name = "user_address")
    private String userAddress;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "username")
    private String username;

    public Order() {
    }

    public Order(
            String userEmail, Double totalCash, String paymentMethod,
            String status, String createDate, String userAddress,
            String firstName, String lastName, String username
    ) {
        this.userEmail = userEmail;
        this.totalCash = totalCash;
        this.status = status;
        this.createDate = createDate;
        this.userAddress = userAddress;
        this.paymentMethod = paymentMethod;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}
