package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum OrderStatus {
    PROCESSING(1), // đơn hàng đang được xử lý
    SHIPPED(2), // ơn hng đã được gửi di
    PENDING(3), // đơn hàng đang chờ xử lý
    DELIVERED(4), // đơn hàng đã được giao
    CANCELLED(5), // đơn hàng đã bị hủy
    RETURNED(6); // đơn hàng đã được trả lại

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
