package com.lark.data.constants;

public enum UserIdTypeEnum {
    OPEN_ID("open_id"),
    USER_ID("user_id");

    private final String value;

    private UserIdTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
