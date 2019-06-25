package org.athena.storage.sdk;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.athena.storage.sdk.credential.Credential;

/**
 * 存储工厂 用来初始化存储实例
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StorageFactory {

    /**
     * 根据存储类型获取对应存储实例
     *
     * @param credential 认证参数
     * @return 存储实例
     */
    public static Storage build(Credential credential) {
        Storage storage = null;
        if ("oss".equals(credential.getType())) {
            storage = new OSSStorage(credential);
        }
        return storage;
    }

}
