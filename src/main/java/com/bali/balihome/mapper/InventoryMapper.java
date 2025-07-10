package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.InventoryRequestDto;
import com.bali.balihome.dto.responsedto.InventoryResponseDto;
import com.bali.balihome.model.domain.Inventory;
import org.mapstruct.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    // create: wire the FK; ignore PK
    @Mapping(source = "dto.productVariantId", target = "productVariant.id")
    @Mapping(target = "id", ignore = true)
    Inventory toEntity(InventoryRequestDto dto);

    // read: extract both the FK and the nested sku
    @Mapping(source = "productVariant.id",  target = "productVariantId")
    @Mapping(source = "productVariant.sku", target = "sku")
    InventoryResponseDto toDto(Inventory inventory);

    // update: same FK wiring
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.productVariantId", target = "productVariant.id")
    void updateEntityFromDto(InventoryRequestDto dto, @MappingTarget Inventory inventory);


}
