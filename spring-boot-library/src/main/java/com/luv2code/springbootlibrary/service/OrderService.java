package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.dao.OrderRepository;
import com.luv2code.springbootlibrary.entity.Order;
import com.luv2code.springbootlibrary.requestmodels.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void postOrder(OrderRequest orderRequest, String userEmail) {
        Order order = new Order();
        long randomNumber = (long) (Math.random() * 90000L) + 10000L;

        order.setId(randomNumber);
        order.setFirstName(orderRequest.getFirstName());
        order.setLastName(orderRequest.getLastName());
        order.setUserEmail(userEmail);
        order.setTotalCash(orderRequest.getTotalCash());
        order.setStatus(orderRequest.getStatus());
        order.setCreateDate(LocalDate.now().toString());
        order.setUserAddress(orderRequest.getUserAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setUsername(orderRequest.getUserName());

        orderRepository.save(order);
    }
}
