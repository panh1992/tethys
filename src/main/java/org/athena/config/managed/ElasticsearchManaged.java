package org.athena.config.managed;

import io.dropwizard.lifecycle.Managed;
import org.athena.common.util.ElasticsearchUtil;
import org.athena.config.configuration.ElasticsearchConfiguration;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ElasticsearchManaged implements Managed {

    private static Logger logger = LoggerFactory.getLogger(RedisManaged.class);

    private RestHighLevelClient client;

    private ElasticsearchConfiguration config;

    public ElasticsearchManaged(ElasticsearchConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() {
        logger.info("Elasticsearch - Starting...");
        this.client = ElasticsearchUtil.getClient(config.getHostname(), config.getPort(), config.getScheme());
        logger.info("Elasticsearch - Start completed");
    }

    @Override
    public void stop() throws IOException {
        logger.info("Elasticsearch - Shutdown initiated...");
        this.client.close();
        logger.info("Elasticsearch - Shutdown completed");
    }

}
