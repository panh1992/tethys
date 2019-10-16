package org.athena.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 系统上下文
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SystemContext {

    private static final ThreadLocal<Long> USER_ID_LOCAL = new ThreadLocal<>();

    /**
     * 获取当前用户id
     */
    public static Long getUserId() {
        return USER_ID_LOCAL.get();
    }

    /**
     * 设置当前用户id
     */
    public static void setUserId(Long userId) {
        USER_ID_LOCAL.set(userId);
    }

    /**
     * 清除当前用户id
     */
    public static void removeUserId() {
        USER_ID_LOCAL.remove();
    }

}
