package com.lari.razorpaybackend.merchant.service.impl;

import com.lari.razorpaybackend.common.exception.DuplicateResourceException;
import com.lari.razorpaybackend.merchant.mapper.MerchantMapper;
import com.lari.razorpaybackend.merchant.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import com.lari.razorpaybackend.common.enums.MerchantStatus;
import com.lari.razorpaybackend.common.enums.UserRole;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.merchant.dto.MerchantResponse;
import com.lari.razorpaybackend.merchant.dto.MerchantSignupRequest;
import com.lari.razorpaybackend.merchant.entity.AppUser;
import com.lari.razorpaybackend.merchant.entity.Merchant;
import com.lari.razorpaybackend.merchant.repository.AppUserRepository;
import com.lari.razorpaybackend.merchant.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;
    private final MerchantMapper merchantMapper;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if (merchantRepository.existsByEmail(request.email())) {
             throw new DuplicateResourceException(ErrorCode.DUPLICATE_MERCHANT_EMAIL);
        }

        Merchant merchant = merchantMapper.toEntityFromMerchantSignupRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);
        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password()) // TODO: encrypt using Bcrypt
                .role(UserRole.OWNER)
                .build();
        
        appUserRepository.save(appUser);
 
        return merchantMapper.toResponse(merchant);
    }

}
