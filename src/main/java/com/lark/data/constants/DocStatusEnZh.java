package com.lark.data.constants;

public enum DocStatusEnZh {
    PENDING("pending", "待迁移"),
    SUCCESS("success", "迁移成功"),
    FAILURE("failure", "迁移失败");

    private final String english;
    private final String chinese;

    private DocStatusEnZh(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public static String getEnglishByChinese(String chinese) {
        for(DocStatusEnZh example : values()) {
            if (example.getChinese().equals(chinese)) {
                return example.getEnglish();
            }
        }

        throw new IllegalArgumentException("Invalid Chinese value: " + chinese);
    }

    public static String getChineseByEnglish(String english) {
        for(DocStatusEnZh example : values()) {
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
