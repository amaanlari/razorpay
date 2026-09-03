package com.lari.razorpaybackend.payment.repository;

import com.lari.razorpaybackend.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOrder_Id(UUID orderId);
}
