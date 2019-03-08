package org.athena;

import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.athena.common.util.CommonUtil;
import org.athena.config.AthenaConfiguration;
import org.athena.config.EnvConfig;

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
        bootstrap.addBundle(new SwaggerBundle<AthenaConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AthenaConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
        bootstrap.setObjectMapper(CommonUtil.getObjectMapper());

    }

    @Override
    public void run(AthenaConfiguration configuration, Environment environment) throws Exception {

        EnvConfig.registerManage(configuration, environment);

        EnvConfig.registerFilter(environment);

        EnvConfig.registerResource(configuration, environment);

        EnvConfig.registerException(environment);

    }

}
