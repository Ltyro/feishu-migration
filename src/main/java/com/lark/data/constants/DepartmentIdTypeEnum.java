package com.lark.data.constants;

public enum DepartmentIdTypeEnum {
    OPEN_DEPARTMENT_ID("open_department_id"),
    DEPARTMENT_ID("department_id");

    private final String value;

    private DepartmentIdTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
