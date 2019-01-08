package org.athena.util.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/**
 * 通用加密工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

    private static final String SALT = BCrypt.gensalt(12);

    /**
     * 获取 uuid 字符串
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");

    }

    /**
     * 使用 BCrypt 加密
     *
     * @param candidate 明文
     */
    public static String hashpw(String candidate) {

        return BCrypt.hashpw(candidate, SALT);

    }

    /**
     * 验证 BCrypt 密码正确性
     *
     * @param candidate 明文
     * @param hashed    密文
     */
    public static boolean checkpw(String candidate, String hashed) {

        return BCrypt.checkpw(candidate, hashed);

    }

}
