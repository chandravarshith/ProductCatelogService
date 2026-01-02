package org.example.paymentservice.services;

import org.example.paymentservice.paymentgateways.IPaymentGateway;
import org.example.paymentservice.paymentgateways.PaymentGatewaySelectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentGatewaySelectionStrategy paymentGatewaySelectionStrategy;

    @Override
    public String getPayementLink(Long amount, String orderId, String description,
                                  String phoneNumber, String name, String email) {
        IPaymentGateway paymentGateway = paymentGatewaySelectionStrategy.getBestPerformingPaymentGateway();
        return paymentGateway.generatePaymentLink(amount, orderId, description, phoneNumber, name, email);
    }
}
