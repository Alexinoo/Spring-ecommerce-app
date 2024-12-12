package com.example.ecommerce.mapper;

import com.example.ecommerce.order.Order;
import com.example.ecommerce.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest newRequest) {
        return Order.builder()
                .id(newRequest.id())
                .customerId(newRequest.customerId())
                .reference(newRequest.reference())
                .totalAmount(newRequest.amount())
                .paymentMethod(newRequest.paymentMethod())
                .build();
    }
}
