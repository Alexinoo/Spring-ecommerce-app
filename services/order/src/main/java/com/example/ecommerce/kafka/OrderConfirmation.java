package com.example.ecommerce.kafka;

import com.example.ecommerce.response.CustomerResponse;
import com.example.ecommerce.paymethod.PaymentMethod;
import com.example.ecommerce.response.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
