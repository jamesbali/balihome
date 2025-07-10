package com.bali.balihome.dto.requestdto;

import com.bali.balihome.model.enums.PaymentMethod;
import com.bali.balihome.model.enums.PaymentStatus;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestDto(

        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Payment amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Payment status is required")
        PaymentStatus paymentStatus,

        String transactionId,

        @NotNull(message = "Payment date is required")
        LocalDateTime paidAt
) {
}
