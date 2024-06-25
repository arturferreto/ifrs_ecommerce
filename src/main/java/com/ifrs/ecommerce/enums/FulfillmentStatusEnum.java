package com.ifrs.ecommerce.enums;

public enum FulfillmentStatusEnum {
    PENDING(0),
    SHIPPED(1),
    DELIVERED(2);

    private final int value;

    FulfillmentStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
