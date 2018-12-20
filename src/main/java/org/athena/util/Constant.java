package org.athena.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 系统常量类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final Integer AUTHORIZATION_DURATION = 120;

    public static final String TIME_ZONE = "UTC";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
