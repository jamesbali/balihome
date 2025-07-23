package com.bali.balihome.repository;

import com.bali.balihome.model.domain.Payment;
import com.bali.balihome.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository <Payment, Long> {
    // Find payments by order
    List<Payment> findByOrderId(Long orderId);

    // Check if transaction ID exists
    boolean existsByTransactionId(String transactionId);

    // For e-commerce payment flow
    Optional<Payment> findByOrderIdAndGatewayPaymentIntentId(Long orderId, String gatewayPaymentIntentId);

    // Find customer payments (assumes Order has Customer with User)
    @Query("SELECT p FROM Payment p JOIN p.order o JOIN o.customer c JOIN c.user u WHERE u.username = :username")
    List<Payment> findByCustomerUsername(@Param("username") String username);

    // Staff tracking
    List<Payment> findByProcessedByUsername(String username);

    // Status tracking
    List<Payment> findByPaymentStatus(PaymentStatus status);

    // Payment method analytics
    @Query("SELECT p.paymentMethod, COUNT(p) FROM Payment p GROUP BY p.paymentMethod")
    List<Object[]> countByPaymentMethod();

    // Revenue by payment source
    @Query("SELECT p.paymentSource, SUM(p.amount) FROM Payment p WHERE p.paymentStatus = 'COMPLETED' GROUP BY p.paymentSource")
    List<Object[]> sumAmountByPaymentSource();



}
