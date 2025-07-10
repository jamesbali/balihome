package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.InventoryRequestDto;
import com.bali.balihome.dto.responsedto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {

    InventoryResponseDto createInventory(InventoryRequestDto dto);

    InventoryResponseDto updateInventory(Long id, InventoryRequestDto dto);

    InventoryResponseDto getInventoryById(Long id);

    List<InventoryResponseDto> getAllInventories();

    InventoryResponseDto getInventoryByVariantId(Long variantId);


    void deleteInventory(Long id);
}
