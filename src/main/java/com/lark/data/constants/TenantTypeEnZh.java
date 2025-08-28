package com.lark.data.constants;

public enum TenantTypeEnZh {
    OLD("old", "迁出（旧）租户"),
    NEW("new", "迁入（新）租户");

    private final String english;
    private final String chinese;

    private TenantTypeEnZh(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public static String getEnglishByChinese(String chinese) {
        for(TenantTypeEnZh example : values()) {
            if (example.getChinese().equals(chinese)) {
                return example.getEnglish();
            }
        }

        throw new IllegalArgumentException("Invalid Chinese value: " + chinese);
    }

    public static String getChineseByEnglish(String english) {
        for(TenantTypeEnZh example : values()) {
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
