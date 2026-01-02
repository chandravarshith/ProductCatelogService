package org.example.paymentservice.services;

public interface IPaymentService {
    String getPayementLink(Long amount, String orderId, String description,
                           String phoneNumber, String name, String email);
}
