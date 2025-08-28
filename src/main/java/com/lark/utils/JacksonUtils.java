package com.lark.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JacksonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    private JacksonUtils() {
    }

    public static <T> List<T> string2List(String json, Class<T> clazz) {
        try {
            return (List)mapper.readValue(json, mapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz}));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> file2List(String fileStr, Class<T> clazz) {
        try {
            File file = new File(fileStr);
            return file2List(file, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> file2List(File file, Class<T> clazz) {
        try {
            return (List)mapper.readValue(file, mapper.getTypeFactory().constructParametricType(List.class, new Class[]{clazz}));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> string2Map(String json) {
        try {
            return (Map)mapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj instanceof String ? (String)obj : mapper.writeValueAsString(obj);
            } catch (Exception e) {
                log.warn("Parse Object to String error", e);
                return null;
            }
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj instanceof String ? (String)obj : mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (Exception e) {
                log.warn("Parse Object to String error", e);
                return null;
            }
        }
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (!StringUtils.isEmpty(str) && clazz != null) {
            try {
                return (T)(clazz.equals(String.class) ? str : mapper.readValue(str, clazz));
            } catch (Exception e) {
                log.warn("Parse String to Object error", e);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (!StringUtils.isEmpty(str) && typeReference != null) {
            try {
                return (T)(typeReference.getType().equals(String.class) ? str : mapper.readValue(str, typeReference));
            } catch (Exception e) {
                log.warn("Parse String to Object error", e);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        try {
            return (T)mapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
