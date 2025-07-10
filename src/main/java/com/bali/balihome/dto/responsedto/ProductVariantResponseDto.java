package com.bali.balihome.dto.responsedto;

import com.bali.balihome.model.enums.VariantColor;
import com.bali.balihome.model.enums.VariantMaterial;
import com.bali.balihome.model.enums.VariantSize;

import java.math.BigDecimal;

public record ProductVariantResponseDto(

        Long id,
        String sku,
        VariantSize size,
        VariantColor color,
        VariantMaterial material,
        BigDecimal priceOverride,
        Integer quantity,
        Long productId

) {
}
