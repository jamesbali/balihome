package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.CustomerRequestDto;
import com.bali.balihome.dto.responsedto.CustomerResponseDto;
import com.bali.balihome.model.domain.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.*;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // create: ignore PK and audit timestamps (JPA will set them)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerRequestDto dto);

    // read: straight 1:1
    CustomerResponseDto toDto(Customer customer);

    // update: only overwrite non-null DTO fields; leave id and timestamps alone
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CustomerRequestDto dto, @MappingTarget Customer customer);


}
