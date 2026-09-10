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
                "{0} not found with {1}: {2}"),
        DUPLICATE_ORDER_RECEIPT(
                "DUPLICATE_ORDER_RECEIPT",
                "Order with receipt already exists for merchant"),
        CANNOT_CANCEL_ORDER(
                "ORDER_CANNOT_CANCEL",
                "Cannot cancel order with status: {0}"),
        ORDER_NOT_PAYABLE(
                "ORDER_NOT_PAYABLE",
                "Order cannot accept payment in status: {0}." );

        private final String code;
        private final String description;
}
