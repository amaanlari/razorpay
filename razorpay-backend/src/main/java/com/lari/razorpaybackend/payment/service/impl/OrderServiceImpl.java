package com.lari.razorpaybackend.payment.service.impl;

import com.lari.razorpaybackend.common.constant.ResourceName;
import com.lari.razorpaybackend.common.enums.OrderStatus;
import com.lari.razorpaybackend.common.exception.BusinessRuleViolationException;
import com.lari.razorpaybackend.common.exception.DuplicateResourceException;
import com.lari.razorpaybackend.common.exception.ResourceNotFoundException;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.payment.dto.request.CreateOrderRequest;
import com.lari.razorpaybackend.payment.dto.response.OrderResponse;
import com.lari.razorpaybackend.payment.dto.response.PaymentResponse;
import com.lari.razorpaybackend.payment.entity.OrderRecord;
import com.lari.razorpaybackend.payment.entity.Payment;
import com.lari.razorpaybackend.payment.mapper.OrderMapper;
import com.lari.razorpaybackend.payment.mapper.PaymentMapper;
import com.lari.razorpaybackend.payment.repository.OrderRepository;
import com.lari.razorpaybackend.payment.repository.PaymentRepository;
import com.lari.razorpaybackend.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @Value("${payment.order.default.order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    @Transactional
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

        return orderMapper.toResponse(orderRecord);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.ORDER,
                        "orderId",
                        orderId.toString()));

        return orderMapper.toResponse(orderRecord);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.ORDER,
                        "orderId",
                        orderId.toString()));

        if (orderRecord.getStatus() == OrderStatus.CANCELLED || orderRecord.getStatus() == OrderStatus.PAID) {
            throw new BusinessRuleViolationException(ErrorCode.CANNOT_CANCEL_ORDER, MessageFormat.format(
                    ErrorCode.CANNOT_CANCEL_ORDER.getDescription(), orderRecord.getStatus().name()));
        }

        orderRecord.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(orderRecord);

        return orderMapper.toResponse(orderRecord);
    }

    @Override
    public List<PaymentResponse> listPayments(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.ORDER,
                        "orderId",
                        orderId.toString()));

        List<Payment> paymentList = paymentRepository.findByOrder_Id(orderId);
        return paymentList.stream().map(paymentMapper::toResponse).collect(Collectors.toList());
    }
}
