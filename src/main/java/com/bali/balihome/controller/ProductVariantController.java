package com.bali.balihome.controller;

import com.bali.balihome.dto.requestdto.ProductVariantRequestDto;
import com.bali.balihome.dto.responsedto.ProductVariantResponseDto;
import com.bali.balihome.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService variantService;


    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ProductVariantResponseDto> create(@Valid @RequestBody ProductVariantRequestDto dto) {
        ProductVariantResponseDto created = variantService.createVariant(dto);
        return ResponseEntity.created(URI.create("/api/v1/variants/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ProductVariantResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(variantService.getVariantById(id));
    }


    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductVariantResponseDto>> getAll() {
        return ResponseEntity.ok(variantService.getAllVariants());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<ProductVariantResponseDto> update(@PathVariable Long id,
                                                            @Valid @RequestBody ProductVariantRequestDto dto) {
        return ResponseEntity.ok(variantService.updateVariant(id, dto));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        variantService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }
}

