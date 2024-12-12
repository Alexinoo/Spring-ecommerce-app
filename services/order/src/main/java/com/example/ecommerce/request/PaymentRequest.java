package com.example.ecommerce.request;

import com.example.ecommerce.paymethod.PaymentMethod;
import com.example.ecommerce.response.CustomerResponse;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
