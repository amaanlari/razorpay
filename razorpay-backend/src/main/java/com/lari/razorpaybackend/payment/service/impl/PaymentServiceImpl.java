package com.lari.razorpaybackend.payment.service.impl;

import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lari.razorpaybackend.common.constant.ResourceName;
import com.lari.razorpaybackend.common.enums.OrderStatus;
import com.lari.razorpaybackend.common.enums.PaymentStatus;
import com.lari.razorpaybackend.common.exception.BusinessRuleViolationException;
import com.lari.razorpaybackend.common.exception.ResourceNotFoundException;
import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import com.lari.razorpaybackend.payment.dto.request.PaymentInitRequest;
import com.lari.razorpaybackend.payment.dto.response.PaymentResponse;
import com.lari.razorpaybackend.payment.entity.OrderRecord;
import com.lari.razorpaybackend.payment.entity.Payment;
import com.lari.razorpaybackend.payment.gateway.PaymentGatewayRouter;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentRequest;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentResult;
import com.lari.razorpaybackend.payment.mapper.PaymentMapper;
import com.lari.razorpaybackend.payment.repository.OrderRepository;
import com.lari.razorpaybackend.payment.repository.PaymentRepository;
import com.lari.razorpaybackend.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentGatewayRouter paymentGatewayRouter;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(request.orderId(), merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.NOT_FOUND,
                        ResourceName.ORDER,
                        "orderId",
                        request.orderId().toString()));

        if (orderRecord.getStatus() != OrderStatus.CREATED && orderRecord.getStatus() != OrderStatus.ATTEMPTED) {
            throw new BusinessRuleViolationException(ErrorCode.ORDER_NOT_PAYABLE, MessageFormat.format(
                    ErrorCode.ORDER_NOT_PAYABLE.getDescription(), orderRecord.getStatus().name()));
        }

        orderRecord.setStatus(OrderStatus.ATTEMPTED);
        orderRecord.setAttempts(orderRecord.getAttempts() + 1);

        Payment payment = Payment.builder()
                .order(orderRecord)
                .merchantId(merchantId)
                .amount(orderRecord.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .methodDetails(request.methodDetails())
                .build();

        payment = paymentRepository.save(payment);
        PaymentRequest paymentRequest = new PaymentRequest(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getMerchantId(),
                payment.getAmount(),
                request.method(),
                request.methodDetails());

        PaymentResult result = paymentGatewayRouter.initiate(paymentRequest);
        switch (result) {
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.registrationRef());
            case PaymentResult.Failure failure -> {
                log.error("Payment failed: {}", result);
                payment.setStatus(PaymentStatus.FAILED);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }
        }

        paymentRepository.save(payment);
        orderRepository.save(orderRecord);

        return paymentMapper.toResponse(payment);
    }
}
