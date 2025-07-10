package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        @NotNull(message = "Product variant ID is required")
        Long productVariantId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
        BigDecimal priceAtPurchase
) {
}
