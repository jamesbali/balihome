package com.bali.balihome.dto.responsedto;

import java.math.BigDecimal;

public record OrderItemResponseDto(

        Long id,
        Long productVariantId,
        String sku,
        int quantity,
        BigDecimal priceAtPurchase
) {
}
