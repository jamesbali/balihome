package com.bali.balihome.mapper;



import com.bali.balihome.dto.requestdto.ProductVariantRequestDto;
import com.bali.balihome.dto.responsedto.ProductVariantResponseDto;
import com.bali.balihome.model.domain.ProductVariant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    // create: wire the FK into product.id, ignore id
    @Mapping(source = "dto.productId", target = "product.id")
    @Mapping(target = "id", ignore = true)
    ProductVariant toEntity(ProductVariantRequestDto dto);

    // read: unwrap product.id back into productId
    @Mapping(source = "product.id", target = "productId")
    ProductVariantResponseDto toDto(ProductVariant variant);

    // update: same as create for nested FK; ignore id
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.productId", target = "product.id")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductVariantRequestDto dto, @MappingTarget ProductVariant entity);
}

