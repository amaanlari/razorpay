package com.lari.razorpaybackend.merchant.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lari.razorpaybackend.common.constant.ResourceName;
import com.lari.razorpaybackend.common.exception.ResourceNotFoundException;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.common.util.RandomizerUtil;
import com.lari.razorpaybackend.merchant.controller.ApiKeyCreateResponse;
import com.lari.razorpaybackend.merchant.dto.CreateApiKeyRequest;
import com.lari.razorpaybackend.merchant.entity.ApiKey;
import com.lari.razorpaybackend.merchant.entity.Merchant;
import com.lari.razorpaybackend.merchant.repository.ApiKeyRepository;
import com.lari.razorpaybackend.merchant.repository.MerchantRepository;
import com.lari.razorpaybackend.merchant.service.ApiKeyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;

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
        
        String keyId = "rzp_" + request.environment().name().toUpperCase() + RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret) // TODO: encrypt with BcryptPasswordEncoder
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

}
