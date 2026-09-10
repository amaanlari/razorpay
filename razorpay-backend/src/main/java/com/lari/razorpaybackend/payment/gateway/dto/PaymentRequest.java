package com.lari.razorpaybackend.payment.gateway.dto;

import java.util.Map;
import java.util.UUID;

import com.lari.razorpaybackend.common.entity.Money;
import com.lari.razorpaybackend.common.enums.PaymentMethod;

public record PaymentRequest(
        UUID paymentId,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentMethod method,
        Map<String, Object> methodDetails
) {
}
