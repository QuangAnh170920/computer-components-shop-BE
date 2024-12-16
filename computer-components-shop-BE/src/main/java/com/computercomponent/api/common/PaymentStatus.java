package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PaymentStatus {
    PENDING("1"), // Chờ thanh toán
    COMPLETED("2"), // Đã thanh toán
    FAILED("3"), // Thanh toán thất bại
    CANCELLED("4"), // Đã hủy
    PROCESSING("5"), // Đang xử lý thanh toán
    ON_HOLD("6"); // Tạm dừng

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static PaymentStatus fromValue(String value) {
        return Stream.of(PaymentStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
