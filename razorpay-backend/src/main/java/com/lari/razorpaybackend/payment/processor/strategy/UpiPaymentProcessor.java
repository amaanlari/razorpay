package com.lari.razorpaybackend.payment.processor.strategy;

import com.lari.razorpaybackend.payment.processor.PaymentProcessor;
import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorRequest;
import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
