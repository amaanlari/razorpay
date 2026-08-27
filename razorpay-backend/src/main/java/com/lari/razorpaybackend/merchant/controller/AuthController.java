package com.lari.razorpaybackend.merchant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lari.razorpaybackend.merchant.dto.MerchantResponse;
import com.lari.razorpaybackend.merchant.dto.MerchantSignupRequest;
import com.lari.razorpaybackend.merchant.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<MerchantResponse> signup(@RequestBody @Valid MerchantSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            authService.signup(request)
        );
    }

}
