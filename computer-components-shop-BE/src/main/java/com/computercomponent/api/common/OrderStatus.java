package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum OrderStatus {
    PROCESSING(1),
    SHIPPED(2),
    IN_TRANSIT(3),
    DELIVERED(4),
    CANCELLED(5),
    FAILED(6);

    private final Integer value;

    OrderStatus(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static OrderStatus fromValue(Integer value) {
        return Stream.of(OrderStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
