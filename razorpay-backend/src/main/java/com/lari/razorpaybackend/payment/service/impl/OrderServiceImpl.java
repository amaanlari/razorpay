package com.lari.razorpaybackend.payment.service.impl;

import com.lari.razorpaybackend.common.enums.OrderStatus;
import com.lari.razorpaybackend.common.exception.DuplicateResourceException;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.payment.dto.request.CreateOrderRequest;
import com.lari.razorpaybackend.payment.dto.response.OrderResponse;
import com.lari.razorpaybackend.payment.entity.OrderRecord;
import com.lari.razorpaybackend.payment.repository.OrderRepository;
import com.lari.razorpaybackend.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Value("${payment.order.default.order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        if (request.receipt() != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            log.warn("Duplicate order receipt rejected. merchantId={}, receipt={}", merchantId, request.receipt());
            throw new DuplicateResourceException(ErrorCode.DUPLICATE_ORDER_RECEIPT);
        }

        OrderRecord orderRecord = OrderRecord.builder()
                .receipt(request.receipt())
                .amount(request.amount())
                .merchantId(merchantId)
                .notes(request.notes())
                .status(OrderStatus.CREATED)
                .expiresAt(request.expiresAt() != null ? request.expiresAt() :
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();

        orderRecord = orderRepository.save(orderRecord);

        // TODO: Publish an event via Kafka to notify other services about the new order creation

        return new OrderResponse(
                orderRepository.save(orderRecord).getId(),
                orderRecord.getMerchantId(),
                orderRecord.getReceipt(),
                orderRecord.getAmount(),
                orderRecord.getStatus(),
                orderRecord.getAttempts(),
                orderRecord.getNotes(),
                orderRecord.getExpiresAt(),
                null
        );
    }
}
