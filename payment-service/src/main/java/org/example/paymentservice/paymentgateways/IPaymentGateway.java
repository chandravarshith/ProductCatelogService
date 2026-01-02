package org.example.paymentservice.paymentgateways;

public interface IPaymentGateway {
    String generatePaymentLink(Long amount, String orderId, String description,
                               String phoneNumber, String name, String email);
}
