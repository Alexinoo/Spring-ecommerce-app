package com.example.ecommerce.service;

import com.example.ecommerce.mapper.PaymentMapper;
import com.example.ecommerce.notification.NotificationProducer;
import com.example.ecommerce.notification.PaymentNotificationRequest;
import com.example.ecommerce.repository.PaymentRepository;
import com.example.ecommerce.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(paymentMapper.toPayment(paymentRequest));

        // send notification
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstname(),
                        paymentRequest.customer().lastname(),
                        paymentRequest.customer().email()
                )
        );

        return payment.getId();
    }
}
