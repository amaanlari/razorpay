package com.lari.razorpaybackend.merchant.dto;

import com.lari.razorpaybackend.common.enums.BusinessType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * MerchantSignupRequest
 */
public record MerchantSignupRequest(

    @NotBlank(message = "{user.name.required}")
    @Size(max = 50, message = "{user.name.max-size}")
    String name,

    @Email
    @NotBlank
    String email,

    @NotBlank(message = "{user.password.required}")
    @Size(min = 8, message = "{user.password.min-size}")
    String password,

    @Size(max = 50, message = "{user.business-name.max-size}")
    String businessName,

    BusinessType businessType
) {

}
