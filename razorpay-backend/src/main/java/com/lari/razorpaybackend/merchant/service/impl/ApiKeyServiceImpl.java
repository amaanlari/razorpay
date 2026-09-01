package com.lari.razorpaybackend.merchant.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.lari.razorpaybackend.merchant.dto.ApiKeyResponse;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;

    @Override
    @Transactional
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

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apiKeyRepository.findByMerchantId(merchantId).stream().map(apiKey -> new ApiKeyResponse(
                apiKey.getId(),
                apiKey.getKeyId(),
                apiKey.getEnvironment(),
                apiKey.isEnabled(),
                apiKey.getLastUsedAt(),
                null
        )).toList();
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey key = apiKeyRepository.findById(keyId)
                .filter(apiKey -> apiKey.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.API_KEY,
                        "key ID",
                        keyId.toString()
                ));

        key.setEnabled(false);
    }

    @Override
    @Transactional
    public ApiKeyCreateResponse rotateKey(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.API_KEY,
                        "key ID",
                        keyId.toString()
                ));

        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret); // TODO: encode with BcryptPasswordEncoder
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(), newRawSecret, apiKey.getEnvironment());
    }
}
