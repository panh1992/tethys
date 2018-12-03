package org.athena.utils;

import java.util.UUID;

/**
 * 通用加密工具
 */
public class CryptoUtils {

    /**
     * 获取 uuid 字符串
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");

    }

}
