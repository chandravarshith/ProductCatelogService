package org.example.paymentservice.dtos.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequestDto {
    private Long amount;
    private String orderId;
    private String description;
    private String phoneNumber;
    private String name;
    private String email;
}
