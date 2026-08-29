package com.lari.razorpaybackend.common.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
        DUPLICATE_MERCHANT_EMAIL(
                        "MERCHANT_001",
                        "Merchant with email already exists"),

        MERCHANT_NOT_FOUND(
                        "MERCHANT_002",
                        "Merchant not found"),

        INVALID_CREDENTIALS(
                        "AUTH_001",
                        "Invalid email or password"),

        NOT_FOUND(
                "{0}_NOT_FOUND",
                "{0} not found with {1}: {2}"
        );

        private final String code;
        private final String description;
}
