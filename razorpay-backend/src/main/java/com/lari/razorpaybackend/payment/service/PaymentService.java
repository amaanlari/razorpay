package com.lari.razorpaybackend.payment.service;

import java.util.UUID;

import com.lari.razorpaybackend.payment.dto.request.PaymentInitRequest;
import com.lari.razorpaybackend.payment.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);

}
