package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PaymentMethod {
    CASH("1"),
    BANK_TRANSFER("2"),
    MOBILE_PAYMENT("3");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static PaymentMethod fromValue(String value) {
        return Stream.of(PaymentMethod.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
