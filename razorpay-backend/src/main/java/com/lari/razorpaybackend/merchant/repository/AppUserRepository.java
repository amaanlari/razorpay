package com.lari.razorpaybackend.merchant.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lari.razorpaybackend.merchant.entity.AppUser;

/**
 * AppUserRepository
 */
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

}
