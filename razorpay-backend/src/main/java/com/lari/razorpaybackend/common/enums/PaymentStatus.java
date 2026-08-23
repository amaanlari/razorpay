package com.lari.razorpaybackend.common.enums;

/**
 * PaymentStatus
 */
public enum PaymentStatus {
    CREATED,
    AUTHROZING,
    AUTHORIZED,
    CAPTURING,
    CAPTURED,
    REFUNDING,
    REFUNDED,
    FAILED,
    CANCELLED,
    PARTIAL_REFUNDED,
    SETTLED,
    AUTH_EXPIRED,
}
