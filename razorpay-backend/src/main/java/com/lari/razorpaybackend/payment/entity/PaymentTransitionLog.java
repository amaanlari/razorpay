package com.lari.razorpaybackend.payment.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lari.razorpaybackend.common.entity.BaseEntity;
import com.lari.razorpaybackend.common.enums.PaymentActor;
import com.lari.razorpaybackend.common.enums.PaymentEvent;
import com.lari.razorpaybackend.common.enums.PaymentStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_transition_log", indexes = {
        @Index(name = "idx_payment_transition_log_payment_id", columnList = "payment_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransitionLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
   
    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(length = 30, nullable = false)
    private PaymentStatus fromStatus;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(length = 30, nullable = false)
    private PaymentStatus toStatus;


    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(length = 30, nullable = false)
    private PaymentEvent eventType;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(length = 100)
    private PaymentActor actor;

    @Column(length = 100)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime occuredAt;
}
