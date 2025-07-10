package com.bali.balihome.repository;

import com.bali.balihome.model.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository <Inventory, Long> {

    Optional<Inventory> findByProductVariantId(Long variantId);

    boolean existsByProductVariantId(Long variantId);

}
