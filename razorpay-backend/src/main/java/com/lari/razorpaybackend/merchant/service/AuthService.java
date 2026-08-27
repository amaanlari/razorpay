package com.lari.razorpaybackend.merchant.service;

import org.jspecify.annotations.Nullable;

import com.lari.razorpaybackend.merchant.dto.MerchantResponse;
import com.lari.razorpaybackend.merchant.dto.MerchantSignupRequest;

public interface AuthService {

    @Nullable
    MerchantResponse signup(MerchantSignupRequest request);
    
}
