package com.lari.razorpaybackend.merchant.dto;

import java.util.UUID;

import com.lari.razorpaybackend.common.enums.BusinessType;
import com.lari.razorpaybackend.common.enums.MerchantStatus;

/**
 * MerchantResponse
 */
public record MerchantResponse(
    UUID id,
    String name,
    String email,
    String businessName,
    BusinessType businessType,
    MerchantStatus merchantStatus
) {

}
