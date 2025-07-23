package com.bali.balihome.service.impl;

import com.bali.balihome.dto.external.PaymentIntent;
import com.bali.balihome.service.PaymentGatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class MockPaymentGatewayService implements PaymentGatewayService {

    @Override
    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency) {
        String intentId = "pi_mock_" + System.currentTimeMillis();
        log.info("Creating mock payment intent for amount: {} {}", amount, currency);

        return new PaymentIntent(
                intentId,
                intentId + "_secret_" + System.currentTimeMillis(),
                amount,
                currency,
                "requires_confirmation",
                null
        );
    }

    @Override
    public PaymentIntent confirmPayment(String paymentIntentId) {
        String transactionId = "ch_mock_" + System.currentTimeMillis();
        log.info("Confirming mock payment intent: {} -> transaction: {}", paymentIntentId, transactionId);

        return new PaymentIntent(
                paymentIntentId,
                null,
                null,
                null,
                "succeeded",
                transactionId
        );
    }

    @Override
    public void refundPayment(String transactionId, BigDecimal amount) {
        log.info("Processing mock refund for transaction: {} amount: {}", transactionId, amount);
        // Mock refund processing
    }
}
