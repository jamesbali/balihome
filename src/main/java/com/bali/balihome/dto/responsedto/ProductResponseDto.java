package com.bali.balihome.dto.responsedto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Integer quantity,
        String category,
        String imageUrl
) {}

