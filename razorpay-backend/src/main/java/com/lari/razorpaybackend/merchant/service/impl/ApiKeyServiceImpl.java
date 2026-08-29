package com.lari.razorpaybackend.merchant.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lari.razorpaybackend.common.constant.ResourceName;
import com.lari.razorpaybackend.common.exception.ResourceNotFoundException;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.merchant.controller.ApiKeyCreateResponse;
import com.lari.razorpaybackend.merchant.dto.CreateApiKeyRequest;
import com.lari.razorpaybackend.merchant.entity.ApiKey;
import com.lari.razorpaybackend.merchant.entity.Merchant;
import com.lari.razorpaybackend.merchant.repository.MerchantRepository;
import com.lari.razorpaybackend.merchant.service.ApiKeyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;

    @Override
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(
            () -> new ResourceNotFoundException(
                ErrorCode.NOT_FOUND, 
                ResourceName.MERCHANT, 
                "id", 
                merchantId.toString()
            )
        );
        
        String keyId = "rzp_" + request.environment().name().toUpperCase()+"big_random_string";
        String rawSecret = "big_random_secret"; // TODO: replace with cryptographic random hex

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .environment(request.environment())
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .build();

        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

}
