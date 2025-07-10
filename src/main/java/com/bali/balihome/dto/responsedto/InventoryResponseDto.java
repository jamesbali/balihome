package com.bali.balihome.dto.responsedto;

public record InventoryResponseDto(

        Long id,
        Long productVariantId,
        String sku,
        int quantity

) {
}
