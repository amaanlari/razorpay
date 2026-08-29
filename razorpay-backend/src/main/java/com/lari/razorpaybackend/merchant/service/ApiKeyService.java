package com.lari.razorpaybackend.merchant.service;

import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.lari.razorpaybackend.merchant.controller.ApiKeyCreateResponse;
import com.lari.razorpaybackend.merchant.dto.CreateApiKeyRequest;

public interface ApiKeyService {

    @Nullable
    ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request);

}
