package com.example.ecommerce.request;

import com.example.ecommerce.payment.Customer;
import com.example.ecommerce.paymethods.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer Id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
