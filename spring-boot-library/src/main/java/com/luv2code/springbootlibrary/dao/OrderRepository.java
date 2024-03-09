package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
