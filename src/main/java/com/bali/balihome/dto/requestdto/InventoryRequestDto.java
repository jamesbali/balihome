package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.*;

public record InventoryRequestDto(

        @NotNull(message = "Product variant ID is required")
        Long productVariantId,

        @Min(value = 0, message = "Quantity must be non-negative")
        int quantity

) {
}
