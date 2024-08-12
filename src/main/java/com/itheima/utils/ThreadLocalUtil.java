package com.itheima.utils;

/**
 * ThreadLocal 工具类
 */
public class ThreadLocalUtil {

    // 提供ThreadLocal对象
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // 获取键值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 存储键值
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    // 清除ThreadLocal 防止内存泄漏
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
