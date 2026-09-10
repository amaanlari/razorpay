package com.lari.razorpaybackend.payment.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lari.razorpaybackend.common.enums.PaymentMethod;
import com.lari.razorpaybackend.payment.processor.PaymentProcessor;
import com.lari.razorpaybackend.payment.processor.strategy.CardPaymentProcessor;
import com.lari.razorpaybackend.payment.processor.strategy.NetBankingPaymentProcessor;
import com.lari.razorpaybackend.payment.processor.strategy.UpiPaymentProcessor;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap() {
        return Map.of(
                PaymentMethod.CARD, new CardPaymentProcessor(),
                PaymentMethod.UPI, new UpiPaymentProcessor(),
                PaymentMethod.NETBANKING, new NetBankingPaymentProcessor()
        );
    }
}
