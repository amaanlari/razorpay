package com.lari.razorpaybackend.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lari.razorpaybackend.common.entity.Money;
import com.lari.razorpaybackend.common.enums.PaymentMethod;
import com.lari.razorpaybackend.common.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentResponse(
        UUID id,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentStatus status,
        PaymentMethod method,
        Map<String, Object> methodDetails,
        String cardLastFour,
        String cardBrand,
        String bankReference,
        String errorCode,
        String errorDescription,
        Long refundedAmountPaise,
        LocalDateTime capturedAt,
        LocalDateTime createdAt
) {
}
