package org.athena.config.managed;

import io.dropwizard.lifecycle.Managed;
import org.athena.netty.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(NettyManaged.class);

    private static Server server = new Server(8090);

    @Override
    public void start() throws Exception {
        logger.info("Netty - Starting...");
        new Thread(() -> {
            try {
                server.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        logger.info("Netty - Start completed");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Netty - Shutdown initiated...");
        server.stop();
        logger.info("Netty - Shutdown completed");
    }

}
