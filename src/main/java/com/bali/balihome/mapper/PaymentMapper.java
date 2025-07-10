package com.bali.balihome.mapper;

import com.bali.balihome.dto.requestdto.PaymentRequestDto;
import com.bali.balihome.dto.responsedto.PaymentResponseDto;
import com.bali.balihome.model.domain.Payment;
import org.mapstruct.*;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    // create: wire the order FK; ignore PK
    @Mapping(source = "dto.orderId", target = "order.id")
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentRequestDto dto);

    // read: unwrap order.id
    @Mapping(source = "order.id", target = "orderId")
    PaymentResponseDto toDto(Payment payment);

    // update: merge any changed fields, re-wire FK if present
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "dto.orderId", target = "order.id")
    void updateEntityFromDto(PaymentRequestDto dto, @MappingTarget Payment payment);
}
