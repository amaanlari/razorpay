package com.lari.razorpaybackend.merchant.entity;

import java.util.UUID;

import com.lari.razorpaybackend.common.enums.BusinessType;
import com.lari.razorpaybackend.common.enums.MerchantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 15)
    private String contactNumber;

    @Column(length = 200)
    private String businessName;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @Column(length = 300)
    private String businessAddress;

    @Column(length = 200)
    private String websiteUrl;

    @Column(nullable = false, length = 200)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status = MerchantStatus.PENDING_KYC;

    @Column(length = 20)
    private String gstId;

    @Column(length = 20)
    private String panId;

    @Column(length = 200)
    private String settlementBankAccount;

    @Column(length = 11)
    private String settlementBankIfsc;

    @Column(length = 200)
    private String settlementBankAccountHolderName;
}
