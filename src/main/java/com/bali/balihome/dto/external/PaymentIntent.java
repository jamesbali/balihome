package com.bali.balihome.dto.external;

import java.math.BigDecimal;

public record PaymentIntent(
        String id,
        String clientSecret,
        BigDecimal amount,
        String currency,
        String status,
        String transactionId
) {
}
