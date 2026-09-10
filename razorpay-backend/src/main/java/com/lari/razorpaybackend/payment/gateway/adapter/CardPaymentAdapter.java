package com.lari.razorpaybackend.payment.gateway.adapter;

import com.lari.razorpaybackend.payment.gateway.PaymentAdapter;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentRequest;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentResult;

public class CardPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult initiate(PaymentRequest request) {
        return null;
    }
}
