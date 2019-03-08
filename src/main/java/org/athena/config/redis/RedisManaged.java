package org.athena.config.redis;

import io.dropwizard.lifecycle.Managed;
import org.athena.common.util.RedisUtil;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(RedisManaged.class);

    private RedissonClient redissonClient;

    private RedisConfig config;

    public RedisManaged(RedisConfig config) {
        this.config = config;
    }

    @Override
    public void start() {
        logger.info("Redis - Starting...");
        this.redissonClient = RedisUtil.getClient(config.getAddress(), config.getPassword(), config.getDb());
        logger.info("Redis - Start completed");
    }

    @Override
    public void stop() {
        logger.info("Redis - Shutdown initiated...");
        redissonClient.shutdown();
        logger.info("Redis - Shutdown completed");
    }

}
