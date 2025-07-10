package com.bali.balihome.service.impl;

import com.bali.balihome.dto.requestdto.InventoryRequestDto;
import com.bali.balihome.dto.responsedto.InventoryResponseDto;
import com.bali.balihome.exception.DuplicateResourceException;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.InventoryMapper;
import com.bali.balihome.model.domain.Inventory;
import com.bali.balihome.model.domain.ProductVariant;
import com.bali.balihome.repository.InventoryRepository;
import com.bali.balihome.repository.ProductVariantRepository;
import com.bali.balihome.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto dto) {
        ProductVariant variant = variantRepository.findById(dto.productVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + dto.productVariantId()));

        if (inventoryRepository.existsByProductVariantId(dto.productVariantId())) {
            throw new DuplicateResourceException("Inventory already exists for this variant.");
        }

        Inventory inventory = inventoryMapper.toEntity(dto);
        inventory.setProductVariant(variant);

        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto updateInventory(Long id, InventoryRequestDto dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));

        inventoryMapper.updateEntityFromDto(dto, inventory);

        // Allow re-linking to another variant
        if (!inventory.getProductVariant().getId().equals(dto.productVariantId())) {
            ProductVariant variant = variantRepository.findById(dto.productVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + dto.productVariantId()));
            inventory.setProductVariant(variant);
        }

        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponseDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        return inventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryResponseDto getInventoryByVariantId(Long variantId) {
        Inventory inventory = inventoryRepository.findByProductVariantId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for variant id: " + variantId));
        return inventoryMapper.toDto(inventory);
    }


    @Override
    public List<InventoryResponseDto> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toDto)
                .toList();
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
    }


}
