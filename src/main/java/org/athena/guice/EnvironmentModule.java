package org.athena.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.config.configuration.AthenaConfiguration;
import org.athena.config.plugin.InstantPlugin;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jpa.JpaPlugin;

public class EnvironmentModule extends AbstractModule {

    /**
     * 注入 Jdbi 实例, 从中获取 数据库操作句柄
     */
    @Provides
    public Jdbi jdbi(AthenaConfiguration configuration, Environment environment) {
        JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDatabase(), "postgres");
        jdbi.installPlugin(new InstantPlugin());
        jdbi.installPlugin(new JpaPlugin());
        return jdbi;
    }

    @Inject
    @Provides
    public SnowflakeIdWorker snowflakeIdWorker(AthenaConfiguration configuration) {
        return new SnowflakeIdWorker(configuration.getSnowflake().getWorkerId(),
                configuration.getSnowflake().getDataCenterId());
    }

}
