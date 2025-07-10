package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.ProductVariantRequestDto;
import com.bali.balihome.dto.responsedto.ProductVariantResponseDto;

import java.util.List;

public interface ProductVariantService {

    ProductVariantResponseDto createVariant(ProductVariantRequestDto dto);

    ProductVariantResponseDto updateVariant(Long id, ProductVariantRequestDto dto);

    ProductVariantResponseDto getVariantById(Long id);

    List<ProductVariantResponseDto> getVariantsByProductId(Long productId);

    List<ProductVariantResponseDto> getAllVariants();

    void deleteVariant(Long id);
}
