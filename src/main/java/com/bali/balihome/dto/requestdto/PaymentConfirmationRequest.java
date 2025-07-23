package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.*;

public record PaymentConfirmationRequest(

        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotBlank(message = "Payment intent ID is required")
        String paymentIntentId
) {
}
