package com.lari.razorpaybackend.merchant.dto;

import com.lari.razorpaybackend.common.enums.Environment;

/**
 * CreateApiKeyRequest
 */
public record CreateApiKeyRequest(
    Environment environment
) {

}
