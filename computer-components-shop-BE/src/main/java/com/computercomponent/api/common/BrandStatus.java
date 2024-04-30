package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum BrandStatus {
    ACTIVE(1),
    DEACTIVATE(2),
    ALL(3);

    private final Integer value;

    BrandStatus(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static BrandStatus fromValue(Integer value) {
        return Stream.of(BrandStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
