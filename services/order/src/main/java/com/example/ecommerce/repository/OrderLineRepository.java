package com.example.ecommerce.repository;

import com.example.ecommerce.orderline.OrderLine;
import com.example.ecommerce.response.OrderLineResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
