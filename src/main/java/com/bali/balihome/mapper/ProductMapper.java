package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.ProductRequestDto;
import com.bali.balihome.dto.responsedto.ProductResponseDto;
import com.bali.balihome.model.domain.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // create: we ignore id and also ignore category (service will set it)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequestDto dto);

    // read: flatten category.name back into DTO.category
    @Mapping(source = "category.name", target = "category")
    ProductResponseDto toDto(Product entity);

    // update: same as create
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductRequestDto dto, @MappingTarget Product product);


}
