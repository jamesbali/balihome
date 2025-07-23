package com.bali.balihome.model.enums;

public enum PaymentStatus {

    PENDING,           // Payment initiated but not completed
    PROCESSING,        // Payment being processed
    COMPLETED,         // Payment successful
    FAILED,           // Payment failed
    CANCELLED,        // Payment cancelled
    REFUNDED,         // Payment refunded
    PARTIALLY_REFUNDED // Partial refund
}
