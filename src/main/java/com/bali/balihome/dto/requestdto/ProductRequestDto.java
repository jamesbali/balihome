package com.bali.balihome.dto.requestdto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequestDto(

        @NotBlank(message = "Product name is required")
        String name,

        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @NotBlank(message = "SKU is required")
        String sku,

        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity,

        @NotBlank(message = "Category is required")
        String category,

        String imageUrl

) {}

