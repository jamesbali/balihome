package com.bali.balihome.model.domain;

import com.bali.balihome.model.enums.VariantColor;
import com.bali.balihome.model.enums.VariantMaterial;
import com.bali.balihome.model.enums.VariantSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Variant SKU is required")
    @Column(nullable = false, unique = true)
    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VariantSize size;

    @Enumerated(EnumType.STRING)
    private VariantColor color;

    @Enumerated(EnumType.STRING)
    private VariantMaterial material;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal priceOverride;

    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
