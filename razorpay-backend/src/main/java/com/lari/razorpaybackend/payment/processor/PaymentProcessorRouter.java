package com.lari.razorpaybackend.payment.processor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lari.razorpaybackend.common.enums.PaymentMethod;
import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorRequest;
import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProcessorRouter {

    private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;

    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        PaymentProcessor paymentProcessor = paymentProcessors.get(request.method());
        if (paymentProcessor == null) {
            throw new IllegalArgumentException("No payment processor found for method: " + request.method());
        }
        return paymentProcessor.charge(request);
    }
}
