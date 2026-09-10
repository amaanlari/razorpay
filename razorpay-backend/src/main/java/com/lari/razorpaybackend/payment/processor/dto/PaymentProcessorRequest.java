package com.lari.razorpaybackend.payment.processor.dto;

import java.util.Map;

import com.lari.razorpaybackend.common.entity.Money;
import com.lari.razorpaybackend.common.enums.PaymentMethod;

public record PaymentProcessorRequest(
        PaymentMethod method,
        Money amount,
        Map<String, Object> methodDetails
) {
}
