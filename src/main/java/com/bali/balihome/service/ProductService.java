package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.ProductRequestDto;
import com.bali.balihome.dto.responsedto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    void deleteProduct(Long id);
}
