package com.lari.razorpaybackend.payment.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lari.razorpaybackend.common.enums.PaymentMethod;
import com.lari.razorpaybackend.payment.gateway.PaymentAdapter;
import com.lari.razorpaybackend.payment.gateway.adapter.CardPaymentAdapter;
import com.lari.razorpaybackend.payment.gateway.adapter.NetBankingAdapter;
import com.lari.razorpaybackend.payment.gateway.adapter.UpiPaymentAdapter;

@Configuration
public class PaymentAdapterConfig {

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap() {
        return Map.of(
                PaymentMethod.CARD, new CardPaymentAdapter(),
                PaymentMethod.UPI, new UpiPaymentAdapter(),
                PaymentMethod.NETBANKING, new NetBankingAdapter()
        );
    }
}
