package com.lari.razorpaybackend.payment.dto.request;

import java.util.Map;
import java.util.UUID;

import com.lari.razorpaybackend.common.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public record PaymentInitRequest(
        @NotNull(message = "{payment.order-id.required}")
        UUID orderId,

        @NotNull(message = "{payment.payment-method.required}")
        PaymentMethod method,

        Map<String, Object> methodDetails
) {
}
