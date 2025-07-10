package com.bali.balihome.service.impl;

import com.bali.balihome.service.ProductService;
import com.bali.balihome.dto.requestdto.ProductRequestDto;
import com.bali.balihome.dto.responsedto.ProductResponseDto;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.ProductMapper;
import com.bali.balihome.model.domain.Product;
import com.bali.balihome.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productMapper.toEntity(requestDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Product updated = productMapper.toEntity(requestDto);
        updated.setId(existing.getId());

        return productMapper.toDto(productRepository.save(updated));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }
}


