package com.lari.razorpaybackend.merchant.dto;

import java.util.UUID;

import com.lari.razorpaybackend.common.enums.Environment;

/**
 * ApiKeyCreateResponse
 */
public record ApiKeyCreateResponse(
    UUID id,
    String keyId,
    String keySecret,
    Environment environment
) {

}
