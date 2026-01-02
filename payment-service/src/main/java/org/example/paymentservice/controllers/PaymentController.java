package org.example.paymentservice.controllers;

import org.example.paymentservice.dtos.dtos.CreatePaymentRequestDto;
import org.example.paymentservice.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody CreatePaymentRequestDto createPaymentRequestDto) {
        return ResponseEntity.ok(this.paymentService.getPayementLink(
                createPaymentRequestDto.getAmount(),
                createPaymentRequestDto.getOrderId(),
                createPaymentRequestDto.getDescription(),
                createPaymentRequestDto.getPhoneNumber(),
                createPaymentRequestDto.getName(),
                createPaymentRequestDto.getEmail()
        ));
    }
}
