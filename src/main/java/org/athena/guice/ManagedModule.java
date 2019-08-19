package org.athena.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.athena.config.configuration.AthenaConfiguration;
import org.athena.config.managed.ElasticsearchManaged;
import org.athena.config.managed.RabbitmqManaged;
import org.athena.config.managed.RedisManaged;
import org.athena.config.managed.SchedulerManaged;
import org.quartz.SchedulerException;

import java.io.IOException;

public class ManagedModule extends AbstractModule {

    @Provides
    public RedisManaged providesRedisManaged(AthenaConfiguration configuration) {
        return new RedisManaged(configuration.getRedis());
    }

    @Provides
    public ElasticsearchManaged providesElasticsearchManaged(AthenaConfiguration configuration) {
        return new ElasticsearchManaged(configuration.getElasticsearch());
    }

    @Provides
    public RabbitmqManaged providesRabbitmqManaged(AthenaConfiguration configuration) {
        return new RabbitmqManaged(configuration.getRabbitmq());
    }

    @Provides
    public SchedulerManaged providesSchedulerManaged() throws IOException, SchedulerException {
        return new SchedulerManaged();
    }

}
