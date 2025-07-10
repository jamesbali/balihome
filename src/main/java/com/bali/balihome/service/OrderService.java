package com.bali.balihome.service;

import com.bali.balihome.dto.requestdto.OrderRequestDto;
import com.bali.balihome.dto.responsedto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto dto);

    OrderResponseDto updateOrder(Long id, OrderRequestDto dto);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getAllOrders();

    void deleteOrder(Long id);
}
