package com.bali.balihome.controller;

import com.bali.balihome.dto.requestdto.OrderRequestDto;
import com.bali.balihome.dto.responsedto.OrderResponseDto;
import com.bali.balihome.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto dto) {
        OrderResponseDto createdOrder = orderService.createOrder(dto);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + createdOrder.id())).body(createdOrder);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Update an order
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id,
                                                        @Valid @RequestBody OrderRequestDto dto) {
        return ResponseEntity.ok(orderService.updateOrder(id, dto));
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
