package com.lark.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    private final Map<String, Object> queryMap = new HashMap();

    private MapUtil() {
    }

    public static MapUtil instance() {
        return new MapUtil();
    }

    public MapUtil put(String key, Object value) {
        if (value != null) {
            this.queryMap.put(key, value);
        }

        return this;
    }

    public Map<String, Object> build() {
        return this.queryMap;
    }
}
