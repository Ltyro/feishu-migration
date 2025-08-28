package com.lark.data.constants;

public class DefaultConstants {
    public static final UserIdTypeEnum userIdType;
    public static final DepartmentIdTypeEnum departmentIdType;

    public DefaultConstants() {
    }

    static {
        userIdType = UserIdTypeEnum.OPEN_ID;
        departmentIdType = DepartmentIdTypeEnum.DEPARTMENT_ID;
    }
}
