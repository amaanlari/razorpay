package com.lari.razorpaybackend.payment.dto.request;

import com.lari.razorpaybackend.common.entity.Money;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Request DTO for creating an order.
 *
 * @param amount The amount for the order.
 * @param receipt The receipt identifier for the order known to the merchant.
 * @param notes Additional notes for the order.
 * @param expiresAt The expiration time for the order.
 */
public record CreateOrderRequest(
        Money amount,
        String receipt,
        Map<String, Object> notes,
        LocalDateTime expiresAt
) {
}
