package org.athena.util;

/**
 * 系统上下文
 */
public final class SystemContext {

    private static final ThreadLocal<String> USER_ID_LOCAL = new ThreadLocal<>();

    private SystemContext() {
    }

    /**
     * 获取当前用户id
     */
    public static String getUserId() {
        return USER_ID_LOCAL.get();
    }

    /**
     * 设置当前用户id
     */
    public static void setUserId(String userId) {
        USER_ID_LOCAL.set(userId);
    }

    /**
     * 清楚当前用户id
     */
    public static void removeUserId() {
        USER_ID_LOCAL.remove();
    }

}
