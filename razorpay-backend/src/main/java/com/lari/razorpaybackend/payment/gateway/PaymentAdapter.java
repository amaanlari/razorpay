package com.lari.razorpaybackend.payment.gateway;

import com.lari.razorpaybackend.payment.gateway.dto.PaymentRequest;
import com.lari.razorpaybackend.payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {

    PaymentResult initiate(PaymentRequest request);

}
