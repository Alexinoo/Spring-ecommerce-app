package com.example.ecommerce.mapper;

import com.example.ecommerce.payment.Payment;
import com.example.ecommerce.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentRequest.Id())
                .orderId(paymentRequest.orderId())
                .paymentMethod((paymentRequest.paymentMethod()))
                .amount(paymentRequest.amount())
                .build();
    }
}
