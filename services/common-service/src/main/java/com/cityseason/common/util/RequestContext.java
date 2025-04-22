package com.cityseason.common.util;

import java.util.HashMap;
import java.util.Map;

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

    public static void remove() {
        CONTEXT.remove();
    }
}