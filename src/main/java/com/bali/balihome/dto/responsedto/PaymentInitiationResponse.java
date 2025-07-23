package com.bali.balihome.dto.responsedto;

import java.math.BigDecimal;

public record PaymentInitiationResponse(

        String clientSecret,        // For frontend payment processing
        String paymentIntentId,     // Gateway payment intent ID
        BigDecimal amount,
        String currency,
        Long orderId
) {
}
