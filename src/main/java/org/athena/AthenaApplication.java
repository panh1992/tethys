package org.athena;

import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.config.AthenaConfiguration;
import org.athena.config.EnvConfig;
import org.athena.utils.SystemContext;

public class AthenaApplication extends Application<AthenaConfiguration> {

    /**
     * Athena 程序启动类
     *
     * @param args 启动参数
     */
    public static void main(String[] args) throws Exception {

        new AthenaApplication().run(args);

    }

    @Override
    public void initialize(Bootstrap<AthenaConfiguration> bootstrap) {

        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new JdbiExceptionsBundle());
        bootstrap.setObjectMapper(SystemContext.getObjectMapper());

    }

    @Override
    public void run(AthenaConfiguration configuration, Environment environment) {

        EnvConfig.registerFilter(environment);

        EnvConfig.registerResource(configuration, environment);

        EnvConfig.registerException(environment);

    }

}
