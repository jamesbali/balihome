package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.CategoryRequestDto;
import com.bali.balihome.dto.responsedto.CategoryResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.bali.balihome.model.domain.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // create: copy name+description; ignore the PK
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDto dto);

    // read: entity → DTO; field names line up 1:1
    CategoryResponseDto toDto(Category category);

    // update: merge into existing—still ignore the PK
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CategoryRequestDto dto, @MappingTarget Category category);
}

