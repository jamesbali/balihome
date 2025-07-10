package com.bali.balihome.dto.requestdto;

import com.bali.balihome.model.enums.VariantColor;
import com.bali.balihome.model.enums.VariantMaterial;
import com.bali.balihome.model.enums.VariantSize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record ProductVariantRequestDto(

        @NotBlank(message = "SKU is required")
        String sku,

        @NotNull(message = "Size is required")
        VariantSize size,

        VariantColor color,

        VariantMaterial material,

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal priceOverride,

        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity,

        @NotNull(message = "Product ID is required")
        Long productId


) {
}
