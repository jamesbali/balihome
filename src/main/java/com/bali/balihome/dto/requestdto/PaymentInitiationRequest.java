package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentInitiationRequest(

        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        String currency  // Optional - defaults to USD

) {
}
