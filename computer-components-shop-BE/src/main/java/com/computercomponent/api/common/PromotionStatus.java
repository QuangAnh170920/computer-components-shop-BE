package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PromotionStatus {
    ACTIVE(1),
    DEACTIVATE(2),
    ALL(3);

    private final Integer value;

    PromotionStatus(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public static PromotionStatus fromValue(Integer value) {
        return Stream.of(PromotionStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
