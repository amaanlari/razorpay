package com.lari.razorpaybackend.operations.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lari.razorpaybackend.common.entity.BaseEntity;
import com.lari.razorpaybackend.common.entity.Money;
import com.lari.razorpaybackend.common.enums.SettlementStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "settlement")
public class Settlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID merchant;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountUnits", column = @Column(name = "gross_amount_units")),
        @AttributeOverride(name = "currency", column = @Column(name = "gross_currency"))
    })
    private Money grossAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountUnits", column = @Column(name = "refund_amount_units")),
        @AttributeOverride(name = "currency", column = @Column(name = "refund_currency"))
    })
    private Money refundAmount;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountUnits", column = @Column(name = "fee_amount_units")),
        @AttributeOverride(name = "currency", column = @Column(name = "fee_currency"))
    })
    private Money feeAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountUnits", column = @Column(name = "gst_amount_units")),
        @AttributeOverride(name = "currency", column = @Column(name = "gst_currency"))
    })
    private Money gstAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amountUnits", column = @Column(name = "net_amount_units")),
        @AttributeOverride(name = "currency", column = @Column(name = "net_currency"))
    })
    private Money netAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SettlementStatus status;
    
    @Column(nullable = false, length = 50)
    private String bankReference;

    private LocalDateTime processedAt;

}
