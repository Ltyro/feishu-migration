package com.lark.data.constants;

public enum UserStatusEnZh {
    PENDING("PENDING", "待扫描"),
    SCANNING("SCANNING", "扫描中"),
    SCANNED("SCANNED", "已扫描"),
    TRANSFERRING("TRANSFERRING", "迁移中"),
    TRANSFERRED("TRANSFERRED", "迁移完成（需检查报告）"),
    SUCCESS("SUCCESS", "迁移成功");

    private final String english;
    private final String chinese;

    private UserStatusEnZh(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public static String getEnglishByChinese(String chinese) {
        for(UserStatusEnZh example : values()) {
            if (example.getChinese().equals(chinese)) {
                return example.getEnglish();
            }
        }

        throw new IllegalArgumentException("Invalid Chinese value: " + chinese);
    }

    public static String getChineseByEnglish(String english) {
        for(UserStatusEnZh example : values()) {
            if (example.getEnglish().equals(english)) {
                return example.getChinese();
            }
        }

        throw new IllegalArgumentException("Invalid English value: " + english);
    }

    public String getEnglish() {
        return this.english;
    }

    public String getChinese() {
        return this.chinese;
    }
}
