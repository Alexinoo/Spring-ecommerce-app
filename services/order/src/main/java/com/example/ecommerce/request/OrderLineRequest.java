package com.example.ecommerce.request;

public record OrderLineRequest(Integer id, Integer orderId, Integer productid, double quantity) {
}
