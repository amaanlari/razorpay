package com.lari.razorpaybackend.payment.processor;

import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorRequest;
import com.lari.razorpaybackend.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);

}
