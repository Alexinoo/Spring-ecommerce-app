package com.example.ecommerce.response;

import com.example.ecommerce.paymethod.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerid
) {
}
