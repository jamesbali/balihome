package com.bali.balihome.model.domain;

import com.bali.balihome.model.enums.PaymentMethod;
import com.bali.balihome.model.enums.PaymentSource;
import com.bali.balihome.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_order_id", columnList = "order_id"),
        @Index(name = "idx_payment_transaction_id", columnList = "transaction_id"),
        @Index(name = "idx_payment_gateway_intent_id", columnList = "gateway_payment_intent_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_source", nullable = false)
    @Builder.Default
    private PaymentSource paymentSource = PaymentSource.IN_STORE;

    // Transaction reference from payment gateway
    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    // Gateway payment intent ID (for e-commerce flow)
    @Column(name = "gateway_payment_intent_id")
    private String gatewayPaymentIntentId;

    // Additional reference (check number, POS terminal, etc.)
    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "currency", length = 3)
    @Builder.Default
    private String currency = "USD";

    // Who processed the payment
    @Column(name = "processed_by_username")
    private String processedByUsername;

    @Column(name = "notes", length = 500)
    private String notes;

    @NotNull
    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
