package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.*;

import java.util.List;

public record OrderRequestDto(

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Order items are required")
        @Size(min = 1, message = "At least one item is required")
        List<OrderItemRequestDto> items
) {
}
