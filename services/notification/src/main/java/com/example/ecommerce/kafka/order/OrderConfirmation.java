package com.example.ecommerce.kafka.order;

import com.example.ecommerce.kafka.customer.CustomerResponse;
import com.example.ecommerce.kafka.payment.PaymentMethod;
import com.example.ecommerce.kafka.product.PurchaseResponse;

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
