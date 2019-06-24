package org.athena.config.managed;

import io.dropwizard.lifecycle.Managed;
import org.athena.common.util.RedisUtil;
import org.athena.config.configuration.RedisConfiguration;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RedisManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(RedisManaged.class);

    private RedissonClient client;

    private List<RedisConfiguration> config;

    public RedisManaged(List<RedisConfiguration> config) {
        this.config = config;
    }

    @Override
    public void start() {
        logger.info("Redis - Starting...");
        this.client = RedisUtil.getClient(config);
        logger.info("Redis - Start completed");
    }

    @Override
    public void stop() {
        logger.info("Redis - Shutdown initiated...");
        this.client.shutdown();
        logger.info("Redis - Shutdown completed");
    }

}
