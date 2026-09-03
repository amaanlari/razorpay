package com.lari.razorpaybackend.payment.service;

import com.lari.razorpaybackend.payment.dto.request.CreateOrderRequest;
import com.lari.razorpaybackend.payment.dto.response.OrderResponse;
import com.lari.razorpaybackend.payment.dto.response.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);
    OrderResponse getById(UUID merchantId, UUID orderId);
    OrderResponse cancel(UUID merchantId, UUID orderId);
    List<PaymentResponse> listPayments(UUID merchantId, UUID orderId);
}
