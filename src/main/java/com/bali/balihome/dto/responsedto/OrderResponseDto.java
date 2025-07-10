package com.bali.balihome.dto.responsedto;

import com.bali.balihome.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Long customerId,
        String customerName,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime placedAt,
        LocalDateTime updatedAt,
        List<OrderItemResponseDto> items
) {
}
