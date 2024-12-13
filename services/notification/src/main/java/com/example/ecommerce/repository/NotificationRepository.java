package com.example.ecommerce.repository;

import com.example.ecommerce.kafka.payment.PaymentConfirmation;
import com.example.ecommerce.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
