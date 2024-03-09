package com.luv2code.springbootlibrary.controller;

import com.luv2code.springbootlibrary.requestmodels.OrderRequest;
import com.luv2code.springbootlibrary.service.OrderService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/secure/add/order")
    public void postOrder(@RequestHeader(value = "Authorization") String token,
                          @RequestBody OrderRequest orderRequest
    ) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if(userEmail == null) {
            throw new Exception("User email is missing");
        }

        orderService.postOrder(orderRequest, userEmail);
    }

}
