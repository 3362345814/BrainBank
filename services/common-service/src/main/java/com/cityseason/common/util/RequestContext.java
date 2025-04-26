package com.cityseason.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestContext {
    private static final ThreadLocal<Map<String, String>> CONTEXT = new ThreadLocal<>();

    public static void init() {
        CONTEXT.set(new HashMap<>());
    }

    public static void set(String key, String value) {
        if (CONTEXT.get() == null) {
            init();
        }
        CONTEXT.get().put(key, value);
    }

    public static String get(String key) {
        if (CONTEXT.get() == null) {
            return null;
        }
        return CONTEXT.get().get(key);
    }

    public static Long getCurrentUserId() {
        // 转换为Long类型
        return Long.parseLong(Objects.requireNonNull(get("x-user-id")));
    }

    public static void remove() {
        CONTEXT.remove();
    }
}