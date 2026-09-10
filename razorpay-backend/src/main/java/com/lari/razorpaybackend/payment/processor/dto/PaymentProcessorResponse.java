package com.lari.razorpaybackend.payment.processor.dto;

public sealed interface PaymentProcessorResponse permits
        PaymentProcessorResponse.Pending,
        PaymentProcessorResponse.Success,
        PaymentProcessorResponse.Failure {

    record Pending(String processorRef) implements PaymentProcessorResponse {}

    record Success(String processorRef, String bankRef) implements PaymentProcessorResponse {}

    record Failure(String processorRef) implements PaymentProcessorResponse {}

}
