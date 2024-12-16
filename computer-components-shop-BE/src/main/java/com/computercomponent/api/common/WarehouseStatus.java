package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum WarehouseStatus {
    PENDING(1),
    APPROVED(2),
    CANCELLED(3);

    private final Integer value;

    WarehouseStatus(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static WarehouseStatus fromValue(Integer value) {
        return Stream.of(WarehouseStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
