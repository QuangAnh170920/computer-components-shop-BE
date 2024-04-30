package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum ProductSpecificationsStatus {
    ACTIVE(1),
    DEACTIVATE(2),
    ALL(3);

    private final Integer value;

    ProductSpecificationsStatus(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static ProductSpecificationsStatus fromValue(Integer value) {
        return Stream.of(ProductSpecificationsStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
