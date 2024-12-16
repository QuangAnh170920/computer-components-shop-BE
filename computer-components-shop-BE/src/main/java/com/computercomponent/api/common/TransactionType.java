package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TransactionType {
    IMPORT(1),
    EXPORT(2);

    private final Integer value;

    TransactionType(Integer value) {
        this.value = value;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
    @JsonCreator
    public static TransactionType fromValue(Integer value) {
        return Stream.of(TransactionType.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
