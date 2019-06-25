package org.athena.config.managed;

import com.rabbitmq.client.Connection;
import io.dropwizard.lifecycle.Managed;
import org.athena.common.util.RabbitmqUtil;
import org.athena.config.configuration.RabbitmqConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitmqManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(RabbitmqManaged.class);

    private Connection connection;

    private RabbitmqConfiguration config;

    public RabbitmqManaged(RabbitmqConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    public void start() throws Exception {
        logger.info("RabbitMQ - Starting...");
        this.connection = RabbitmqUtil.getConnection(config.getHost(), config.getUsername(),
                config.getPassword(), config.getVirtualHost());
        logger.info("RabbitMQ - Start completed");
    }

    @Override
    public void stop() throws Exception {
        logger.info("RabbitMQ - Shutdown initiated...");
        this.connection.close();
        logger.info("RabbitMQ - Shutdown completed");
    }

}
