package org.athena.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisUtil {

    private static volatile RedissonClient client;

    /**
     * 获取 RedisClient
     */
    public static RedissonClient getClient(String address, String password, Integer db) {
        if (client == null) {
            synchronized (RedisUtil.class) {
                if (client == null) {
                    Config config = new Config();
                    config.setTransportMode(TransportMode.NIO);
                    config.useSingleServer().setAddress(address);
                    config.useSingleServer().setPassword(password);
                    config.useSingleServer().setDatabase(db);
                    client = Redisson.create(config);
                }
            }
        }
        return client;
    }

    public static String getString(String key) {
        RBucket<String> test = client.getBucket(key);
        return test.isExists() ? test.get() : null;
    }

    public static void setString(String key, String value) {
        client.getBucket(key).set(value);
    }

    public static void setString(String key, String value, long timeout) {
        client.getBucket(key).set(value, timeout, TimeUnit.SECONDS);
    }

    public static void setString(String key, String value, long timeout, TimeUnit unit) {
        client.getBucket(key).set(value, timeout, unit);
    }

}
