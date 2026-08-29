package com.lari.razorpaybackend.merchant.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lari.razorpaybackend.merchant.dto.CreateApiKeyRequest;
import com.lari.razorpaybackend.merchant.service.ApiKeyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/merchants/{merchantId}/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ResponseEntity<ApiKeyCreateResponse> create(
        @PathVariable UUID merchantId,
        @Valid @RequestBody CreateApiKeyRequest request) {
        
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(apiKeyService.create(request))
    }
}
