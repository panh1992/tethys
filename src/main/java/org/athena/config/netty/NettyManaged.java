package org.athena.config.netty;

import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(NettyManaged.class);

    @Override
    public void start() throws Exception {
        logger.debug("netty service start");
    }

    @Override
    public void stop() throws Exception {
        logger.debug("netty service stop");
    }

}
