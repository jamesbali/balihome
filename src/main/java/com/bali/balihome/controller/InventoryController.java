package com.bali.balihome.controller;

import com.bali.balihome.dto.requestdto.InventoryRequestDto;
import com.bali.balihome.dto.responsedto.InventoryResponseDto;
import com.bali.balihome.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService inventoryService;

    // Create new inventory record
    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryRequestDto dto) {
        InventoryResponseDto created = inventoryService.createInventory(dto);
        return ResponseEntity.created(URI.create("/api/v1/inventory/" + created.id())).body(created);
    }

    // Update inventory quantity
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> update(@PathVariable Long id,
                                                       @Valid @RequestBody InventoryRequestDto dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    // Get inventory by variant ID
    @GetMapping("/variant/{variantId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> getByVariantId(@PathVariable Long variantId) {
        return ResponseEntity.ok(inventoryService.getInventoryByVariantId(variantId));
    }

    // Get all inventory records
    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<InventoryResponseDto>> getAll() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    // Delete inventory record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
