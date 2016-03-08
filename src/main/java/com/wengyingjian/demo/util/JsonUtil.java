package com.wengyingjian.demo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by wengyingjian on 16/3/8.
 */
public class JsonUtil {
    private static ObjectMapper JSON = new ObjectMapper();

    public JsonUtil() {
    }

    public static String getJsonFromObject(Object obj) {
        try {
            return JSON.writeValueAsString(obj);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T getObjectFromJson(String json, Class<T> valueType) {
        try {
            return JSON.readValue(json, valueType);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T getObjectFromJson(String json, TypeReference<T> valueTupeRef) {
        try {
            return JSON.readValue(json, valueTupeRef);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
