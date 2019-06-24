package org.athena.config.managed;

import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(NettyManaged.class);

    @Override
    public void start() throws Exception {
        logger.info("Netty - Starting...");
        logger.info("Netty - Start completed");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Netty - Shutdown initiated...");
        logger.info("Netty - Shutdown completed");
    }

}
