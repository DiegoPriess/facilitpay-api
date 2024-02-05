package com.facilitipay.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

@AllArgsConstructor
public enum TypeValue {

    PERCENTAGE("%"),
    REAL("R$");

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static synchronized TypeValue create(final HashMap<?, ?> value) {
        return TypeValue.getByName(value.get("name").toString());
    }

    public static synchronized TypeValue getByName(final String name) {
        return Arrays.stream(TypeValue.values()).filter(filter -> filter.name().equals(name)).findFirst().orElse(null);
    }

    private String displayValue;
}