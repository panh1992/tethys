package org.athena.common.storage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 存储工厂 用来初始化存储实例
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StorageFactory {

    /**
     * 根据存储类型获取对应存储实例
     *
     * @param type 存储类型
     * @return 存储实例
     */
    public static Storage build(String type, Credential credential) {
        Storage storage = null;
        if ("oss".equals(type)) {
            storage = new OSSStorage(credential);
        }
        return storage;
    }


}
