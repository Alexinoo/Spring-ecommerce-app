package com.example.ecommerce.kafka.order;

import com.example.ecommerce.kafka.customer.Customer;
import com.example.ecommerce.kafka.payment.PaymentMethod;
import com.example.ecommerce.kafka.product.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}
