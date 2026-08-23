package com.lari.razorpaybackend.operations.entity;

import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class SettlementPaymentId {

    private UUID settlementId;
    private UUID paymentId;

}
