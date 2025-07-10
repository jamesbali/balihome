package com.bali.balihome.repository;

import com.bali.balihome.model.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Optional: fetch all items belonging to a specific order
    List<OrderItem> findByOrderId(Long orderId);


}
