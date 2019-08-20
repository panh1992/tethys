package org.athena;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.common.util.CommonUtil;
import org.athena.config.EnvConfig;
import org.athena.config.bundle.SwaggerBundle;
import org.athena.config.configuration.AthenaConfiguration;
import org.athena.guice.EnvironmentModule;
import org.athena.guice.ManagedModule;
import org.athena.guice.RepositoryModule;
import org.athena.guice.dropwizard.GuiceBundle;

public class AthenaApplication extends Application<AthenaConfiguration> {

    private static GuiceBundle<AthenaConfiguration> guiceBundle;

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

        guiceBundle = GuiceBundle.<AthenaConfiguration>newBuilder().addModule(new ManagedModule())
                .addModule(new EnvironmentModule()).addModule(new RepositoryModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(AthenaConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new JdbiExceptionsBundle());
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.setObjectMapper(guiceBundle.getInjector().getInstance(ObjectMapper.class));

    }

    @Override
    public void run(AthenaConfiguration configuration, Environment environment) {

        EnvConfig.registerCors(environment, configuration.getCors());

        EnvConfig.registerException(environment);

        EnvConfig.registerFilter(environment, guiceBundle);

    }

}
