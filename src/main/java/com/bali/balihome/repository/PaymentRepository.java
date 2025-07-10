package com.bali.balihome.repository;

import com.bali.balihome.model.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository <Payment, Long> {

    // Find all payments related to a specific order
    List<Payment> findByOrderId(Long orderId);

    // Optionally: Find payment by transaction ID if needed
    boolean existsByTransactionId(String transactionId);

}
