package com.example.ecommerce.mapper;

import com.example.ecommerce.order.Order;
import com.example.ecommerce.orderline.OrderLine;
import com.example.ecommerce.request.OrderLineRequest;
import com.example.ecommerce.response.OrderLineResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(Order.builder()
                        .id(request.orderId())
                        .build()
                )
                .productId(request.productid())
                .build();

    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
