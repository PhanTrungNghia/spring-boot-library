package com.luv2code.springbootlibrary.requestmodels;

import lombok.Data;

@Data
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String userEmail;
    private String paymentMethod;
    private String deliveryMethod;
    private String status;
    private String createDate;
    private String userAddress;
    private Double totalCash;

}
