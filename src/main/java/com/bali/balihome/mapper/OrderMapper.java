package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.OrderRequestDto;
import com.bali.balihome.dto.responsedto.OrderResponseDto;
import com.bali.balihome.model.domain.Order;
import org.mapstruct.*;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    // create: wire in the customer FK; ignore PK and audit fields
    @Mapping(source = "dto.customerId", target = "customer.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "placedAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequestDto dto);

    // read: unwrap customer.id, build customerName, map items
    @Mapping(source = "customer.id",                                                  target = "customerId")
    @Mapping(expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())",
            target = "customerName")
    OrderResponseDto toDto(Order order);

    // update: just merge new item list or customer FK if you like
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.customerId", target = "customer.id")
    void updateEntityFromDto(OrderRequestDto dto, @MappingTarget Order entity);
}
