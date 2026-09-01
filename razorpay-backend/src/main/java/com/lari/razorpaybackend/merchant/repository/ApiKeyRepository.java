package com.lari.razorpaybackend.merchant.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lari.razorpaybackend.merchant.entity.ApiKey;

/**
 * ApiKeyRepository
 */
public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID>{

    List<ApiKey> findByMerchantId(UUID merchantId);
}
