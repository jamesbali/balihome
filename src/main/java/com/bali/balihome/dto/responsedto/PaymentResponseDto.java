package com.bali.balihome.dto.responsedto;

import com.bali.balihome.model.enums.PaymentMethod;
import com.bali.balihome.model.enums.PaymentSource;
import com.bali.balihome.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto (
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        PaymentSource paymentSource,
        String transactionId,
        String referenceNumber,
        String currency,
        String processedByUsername,
        String notes,
        LocalDateTime paidAt,
        LocalDateTime createdAt,
        Long orderId
) {
}
