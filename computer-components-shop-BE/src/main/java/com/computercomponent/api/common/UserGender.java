package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

public enum UserGender {
    FEMALE(2),
    OTHER(3),
    MALE(1);

    @Getter(onMethod_ = @JsonValue)
    private final Integer value;

    UserGender(Integer value) {
        this.value = value;
    }

    public static UserGender fromValue(Integer value) {
        return Stream.of(UserGender.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElse(null);
    }
}
