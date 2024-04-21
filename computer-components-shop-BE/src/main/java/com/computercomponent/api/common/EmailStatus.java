package com.computercomponent.api.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum EmailStatus {
    SUCCESS(0),
    FAIL(1);

    @Getter(onMethod_ = @JsonValue)
    private final Integer value;

    EmailStatus(Integer value) {
        this.value = value;
    }
}
