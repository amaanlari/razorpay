
package com.lari.razorpaybackend.merchant.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lari.razorpaybackend.merchant.entity.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    boolean existsByEmail(String email);

}
