package com.lari.razorpaybackend.merchant.service;

import java.util.List;
import java.util.UUID;

import com.lari.razorpaybackend.merchant.controller.ApiKeyCreateResponse;
import com.lari.razorpaybackend.merchant.dto.ApiKeyResponse;
import com.lari.razorpaybackend.merchant.dto.CreateApiKeyRequest;

public interface ApiKeyService {

    ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    ApiKeyCreateResponse rotateKey(UUID merchantId, UUID keyId);
}
