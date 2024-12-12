package com.example.ecommerce.repository;

import com.example.ecommerce.orderline.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
}
