package com.lari.razorpaybackend.payment.gateway;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lari.razorpaybackend.common.enums.PaymentMethod;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentRequest;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final Map<PaymentMethod, PaymentAdapter> paymentAdapters;

    public PaymentResult initiate(PaymentRequest request) {
        PaymentAdapter paymentAdapter = paymentAdapters.get(request.method());
        if (paymentAdapter == null) {
            throw new IllegalArgumentException("No payment adapter found for method: " + request.method());
        }
        return paymentAdapter.initiate(request);
    }
}
