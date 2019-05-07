package org.athena.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtil {

    /**
     * 判断是否进行分页处理
     *
     * @param page 分页页码
     * @param size 每页数量
     * @return true:是  false:否
     */
    public static boolean isPage(Integer page, Integer size) {
        return Objects.isNull(page) || Objects.isNull(size);
    }

}
