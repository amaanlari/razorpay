package com.lari.razorpaybackend.payment.service;

import com.lari.razorpaybackend.payment.dto.request.CreateOrderRequest;
import com.lari.razorpaybackend.payment.dto.response.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);
}
