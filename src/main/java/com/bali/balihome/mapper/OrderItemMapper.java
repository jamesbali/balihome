package com.bali.balihome.mapper;


import com.bali.balihome.dto.requestdto.OrderItemRequestDto;
import com.bali.balihome.dto.responsedto.OrderItemResponseDto;
import com.bali.balihome.model.domain.OrderItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    // create: wire variant FK; ignore PK+order (service will set order)
    @Mapping(source = "dto.productVariantId", target = "productVariant.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequestDto dto);

    // read: unwrap both variant FK and its sku
    @Mapping(source = "productVariant.id",  target = "productVariantId")
    @Mapping(source = "productVariant.sku", target = "sku")
    OrderItemResponseDto toDto(OrderItem entity);

    // update: same wiring; keep existing order/id
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.productVariantId", target = "productVariant.id")
    @Mapping(target = "order", ignore = true)
    void updateEntityFromDto(OrderItemRequestDto dto, @MappingTarget OrderItem entity);


}
