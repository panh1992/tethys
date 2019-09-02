package org.athena;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.JdbiHealthCheck;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.jobs.JobsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.common.util.CommonUtil;
import org.athena.config.EnvConfig;
import org.athena.config.bundle.SwaggerBundle;
import org.athena.config.configuration.AthenaConfiguration;
import org.athena.guice.EnvironmentModule;
import org.athena.guice.JDBIFactory;
import org.athena.task.DemoJob;
import org.jdbi.v3.core.Jdbi;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.guicey.jdbi3.JdbiBundle;

public class AthenaApplication extends Application<AthenaConfiguration> {

    private static GuiceBundle<Configuration> guiceBundle;

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

        guiceBundle = GuiceBundle.builder().bundles(JdbiBundle.forDbi(JDBIFactory::get))
                .modules(new EnvironmentModule()).enableAutoConfig(getClass().getPackage().getName())
                .useWebInstallers().build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new JobsBundle(new DemoJob()));
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new JdbiExceptionsBundle());
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.setObjectMapper(CommonUtil.getObjectMapper());

    }

    @Override
    public void run(AthenaConfiguration configuration, Environment environment) {

        EnvConfig.registerCors(environment, configuration.getCors());

        EnvConfig.registerException(environment);

        EnvConfig.registerFilter(environment, guiceBundle);

        environment.healthChecks().register("dataBaseHealthCheck", new JdbiHealthCheck(guiceBundle
                .getInjector().getInstance(Jdbi.class), "/* Health Check */ SELECT 1"));

    }

}
