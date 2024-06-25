package com.ifrs.ecommerce.enums;

public enum PaymentStatusEnum {
    PENDING(0),
    PAID(1),
    CANCELED(2);

    private final int value;

    PaymentStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
