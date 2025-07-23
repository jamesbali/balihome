package com.bali.balihome.model.enums;

public enum PaymentMethod {

    // Card payments (covers all card types)
    CARD,

    // Digital wallets
    PAYPAL,
    APPLE_PAY,
    GOOGLE_PAY,

    // Traditional methods
    CASH,
    CHECK,
    BANK_TRANSFER,

    // Store-specific
    STORE_CREDIT,
    GIFT_CARD,

    // Business methods
    FINANCING,
    CASH_ON_DELIVERY,
    MOBILE_MONEY

}
