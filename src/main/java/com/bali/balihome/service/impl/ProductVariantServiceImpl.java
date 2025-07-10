package com.bali.balihome.service.impl;

import com.bali.balihome.dto.requestdto.ProductVariantRequestDto;
import com.bali.balihome.dto.responsedto.ProductVariantResponseDto;
import com.bali.balihome.exception.DuplicateResourceException;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.ProductVariantMapper;
import com.bali.balihome.model.domain.Product;
import com.bali.balihome.model.domain.ProductVariant;
import com.bali.balihome.repository.ProductRepository;
import com.bali.balihome.repository.ProductVariantRepository;
import com.bali.balihome.service.ProductVariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper variantMapper;

    @Override
    public ProductVariantResponseDto createVariant(ProductVariantRequestDto dto) {
        if (variantRepository.existsBySku(dto.sku())) {
            throw new DuplicateResourceException("Product variant with SKU already exists: " + dto.sku());
        }

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.productId()));

        ProductVariant variant = variantMapper.toEntity(dto);
        variant.setProduct(product);

        return variantMapper.toDto(variantRepository.save(variant));
    }

    @Override
    public ProductVariantResponseDto updateVariant(Long id, ProductVariantRequestDto dto) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));

        variantMapper.updateEntityFromDto(dto, variant);

        // Re-link product if changed
        if (!variant.getProduct().getId().equals(dto.productId())) {
            Product newProduct = productRepository.findById(dto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.productId()));
            variant.setProduct(newProduct);
        }

        return variantMapper.toDto(variantRepository.save(variant));
    }

    @Override
    public ProductVariantResponseDto getVariantById(Long id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));
        return variantMapper.toDto(variant);
    }

    @Override
    public List<ProductVariantResponseDto> getVariantsByProductId(Long productId) {
        List<ProductVariant> variants = variantRepository.findByProductId(productId);
        return variants.stream().map(variantMapper::toDto).toList();
    }
    @Override
    public List<ProductVariantResponseDto> getAllVariants() {
        List<ProductVariant> variants = variantRepository.findAll();
        return variants.stream()
                .map(variantMapper::toDto)
                .toList();
    }

    @Override
    public void deleteVariant(Long id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));
        variantRepository.delete(variant);
    }


}
