package com.bali.balihome.service.impl;

import com.bali.balihome.dto.requestdto.OrderRequestDto;
import com.bali.balihome.dto.responsedto.OrderResponseDto;
import com.bali.balihome.exception.ResourceNotFoundException;
import com.bali.balihome.mapper.OrderItemMapper;
import com.bali.balihome.mapper.OrderMapper;
import com.bali.balihome.model.domain.Order;
import com.bali.balihome.model.domain.OrderItem;
import com.bali.balihome.model.domain.ProductVariant;
import com.bali.balihome.repository.OrderRepository;
import com.bali.balihome.repository.ProductVariantRepository;
import com.bali.balihome.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductVariantRepository variantRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = orderMapper.toEntity(dto);

        // Manually set OrderItems with associated ProductVariant
        List<OrderItem> items = dto.items().stream().map(itemDto -> {
            ProductVariant variant = variantRepository.findById(itemDto.productVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Variant not found with ID: " + itemDto.productVariantId()));
            OrderItem item = orderItemMapper.toEntity(itemDto);
            item.setProductVariant(variant);
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        // Clear existing items (if needed for full replace)
        order.getItems().clear();

        List<OrderItem> updatedItems = dto.items().stream().map(itemDto -> {
            ProductVariant variant = variantRepository.findById(itemDto.productVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Variant not found with ID: " + itemDto.productVariantId()));
            OrderItem item = orderItemMapper.toEntity(itemDto);
            item.setProductVariant(variant);
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        orderMapper.updateEntityFromDto(dto, order);
        order.setItems(updatedItems);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        orderRepository.delete(order);
    }
}
