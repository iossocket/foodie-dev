package com.iossocket.enums;

public enum Gender {
    female(0, "女"),
    male(1, "男"),
    unknown(2, "保密");

    public final Integer type;
    public final String value;

    Gender(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
