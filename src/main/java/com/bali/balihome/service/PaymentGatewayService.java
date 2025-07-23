package com.bali.balihome.service;

import com.bali.balihome.dto.external.PaymentIntent;

import java.math.BigDecimal;

public interface PaymentGatewayService {

    PaymentIntent createPaymentIntent(BigDecimal amount, String currency);
    PaymentIntent confirmPayment(String paymentIntentId);
    void refundPayment(String transactionId, BigDecimal amount);

}
